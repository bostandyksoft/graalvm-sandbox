package org.example;

import org.graalvm.polyglot.*;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		boolean suspend = Boolean.parseBoolean(System.getenv("suspend"));

		Context.Builder builder = Context.newBuilder("js")
				.allowAllAccess(false)
				.allowIO(true)
				.allowExperimentalOptions(true)
				.option("js.esm-eval-returns-exports", "true")
				.allowPolyglotAccess(
						PolyglotAccess.newBuilder()
								.allowBindingsAccess("js")
								.build()
				)
				.allowHostAccess(
						HostAccess.newBuilder()
								//EXPLICIT
								.allowAccessAnnotatedBy(HostAccess.Export.class)
								.allowImplementationsAnnotatedBy(HostAccess.Implementable.class)
								.allowImplementationsAnnotatedBy(FunctionalInterface.class)
								//Поддержка массивов
								.allowArrayAccess(true)
								.allowListAccess(true)
								.build()
				).
				option("inspect", "4242").
				option("inspect.Path", "test").
				option("inspect.Suspend", Boolean.toString(suspend));

		try (Context context = builder.build()) {
			Value value = context.eval(
					Source.newBuilder(
							"js",
							"""
									let a = 1;
									let b = 2;
									let c = a+b;
									console.log(c);
																	
									export const A = `${c}`;
									""",
							"script.mjs"
					).build());
			System.out.println(value.getMember("A").asString());
		}
	}

}
