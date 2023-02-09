package ar.com.macro.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

/**
 *  0: si Identity-X nos indicó que el envió del video produjo un FAILED
 *  1: si todo salió OK y el confidence mínimo se superó
 *  2: si todo salio OK pero en confidence mínimo no se cumplió
 */
@Getter
@AllArgsConstructor
public enum Status {

	FAILED(0),
	SUCCESS(1),
	FAILED_CONFIDENCE(2);
	
	public final Integer value;
	
	public static Stream<Status> stream() {
        return Stream.of(Status.values());
    }

}



