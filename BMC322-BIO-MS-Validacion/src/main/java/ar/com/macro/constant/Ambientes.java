package ar.com.macro.constant;

import java.util.Optional;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Ambientes {

	LOCAL("local"),
	DEV("dev1"),
	STAGE("stg1"),
	LAB("lab1"),
	TEST("test1"),
	PREPROD1("pre-prd1"),
	PREPROD2("pre-prd2"),
	PROD1("prd1"),
	PROD2("prd1");
	
	public final String ambiente;
	
	public static Stream<Ambientes> stream() {
        return Stream.of(Ambientes.values());
    }
	
	public static Optional<Ambientes> obtenerAmbiente(String ambiente) {
        for (Ambientes enumAmbiente : Ambientes.values()) {
            if (enumAmbiente.getAmbiente().equals(ambiente)) {
                return Optional.of(enumAmbiente);
            }
        }
        return Optional.empty();
    }
}
