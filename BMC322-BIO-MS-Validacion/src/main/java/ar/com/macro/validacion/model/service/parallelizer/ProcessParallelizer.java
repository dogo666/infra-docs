package ar.com.macro.validacion.model.service.parallelizer;

import static java.util.Objects.nonNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * ProcessParallelizer.
 *
 * @author globant
 * @version 1.0
 * @since 2022-04-28
 */
@Slf4j
public class ProcessParallelizer<T> {

  private static final String STOP_CONDITION_MSG =
      "Se cumple la condición de parada. No se esperan respuestas adicionales.";

  private CompletableFuture<Void> completableFuture;

  public List<T> callAndJoin(final List<ParallelJob> jobs, final ExecutorService executor) {
    return callAndJoin(jobs, executor);
  }

  @SuppressWarnings("unchecked")
  public List<T> callAndJoin(
      final List<ParallelJob> jobs,
      final ExecutorService executor,
      final Predicate<T> stopCondition) {

    var store = Collections.synchronizedList(new ArrayList<T>());

    try {
      var futurejobs =
          jobs.stream()
              .map(
                  job ->
                      supplyAsync(job.getCallExpression(), executor)
                          .thenAccept(
                              result -> {
                                store.add((T) result);

                                if (nonNull(stopCondition) && stopCondition.test((T) result)) {
                                  stopExecution(executor);
                                }
                              }))
              .toArray(CompletableFuture[]::new);

      completableFuture = CompletableFuture.allOf(futurejobs);
      completableFuture.join();

    } catch (CompletionException e) {
      if (e.getCause() instanceof StopProcessException) {
        log.debug(STOP_CONDITION_MSG);
      } else {
        throw e;
      }
    }

    return store;
  }

  /**
   * Detiene la espera de resultados futuros
   *
   * @param executor
   */
  private void stopExecution(final ExecutorService executor) {
    completableFuture.completeExceptionally(new StopProcessException(STOP_CONDITION_MSG));
    executor.shutdownNow();
  }

  /**
   * StopProcessException.
   *
   * @author globant
   * @version 1.0
   * @since 2022-04-29
   */
  static class StopProcessException extends RuntimeException {
    private static final long serialVersionUID = 164901154754912969L;

    StopProcessException(final String message) {
      super(message);
    }
  }

  /**
   * ParallelJob.
   *
   * @author globant
   * @version 1.0
   * @since 2022-04-28
   */
  @Getter
  @AllArgsConstructor(staticName = "of")
  public static class ParallelJob {

    Supplier<Object> callExpression;
  }
}
