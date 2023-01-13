package com.yipw.reactivepractice;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ReactivePracticeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testStringMonoJust() {
		String data = "Abc";
		StepVerifier.create(Mono.just(data)).expectNext(data).verifyComplete();
	}

	@Test
	void testStringMonoMap() {
		Mono<String> monoString = Mono.just("ABC");
		StepVerifier.create(monoString.map((value) -> {
			return value.toLowerCase();
		})).expectNext("abc").verifyComplete();
	}

	@Test
	void testStringListMonoJust() {
		List<String> stringList = Arrays.asList("A", "B", "C");
		StepVerifier.create(Mono.just(stringList)).expectNext(stringList).verifyComplete();
	}

	@Test
	void testStringListMonoMap() {
		Mono<List<String>> stringListMono = Mono.just(Arrays.asList("A", "B", "C"));
		StepVerifier.create(stringListMono.map((List<String> list) -> {
			list.replaceAll((value) -> {
				return value.toLowerCase();
			});
			return list;
		})).expectNext(Arrays.asList("a", "b", "c")).verifyComplete();
	}

	@Test
	void testStringMonoZip() {
		Mono<String> aMono = Mono.just("A");
		Mono<String> bMono = Mono.just("B");
		StepVerifier.create(Mono.zip(aMono, bMono, (a,b)->a+b)).expectNext("AB").verifyComplete();
	}

	@Test
	void testStringFluxJust() {
		StepVerifier.create(Flux.just("A", "B", "C")).expectNext("A")
		.expectNext("B").expectNext("C").verifyComplete();
	}

	@Test
	void testStringFluxMap() {
		Flux<String> fluxString = Flux.just("A", "B", "C");
		StepVerifier.create(fluxString.map((value) -> {
			return value.toLowerCase();
		})).expectNext("a").expectNext("b").expectNext("c").verifyComplete();
	}

	@Test
	void testIntFluxRange() {
		StepVerifier.create(Flux.range(1, 5)).expectNext(1).expectNext(2).expectNextCount(3).verifyComplete();
	}

	@Test
	void testIntFluxRangeFilter() {
		StepVerifier.create(Flux.range(1, 30).filter(x -> x % 5 == 0)).expectNext(5).expectNext(10).expectNextCount(4).verifyComplete();
	}

	@Test
	void testStringFluxConcat() {
		Flux<String> abcFlux = Flux.just("A", "B", "C");
		Flux<String> xyzFlux = Flux.just("X", "Y", "Z");
		StepVerifier.create(Flux.concat(abcFlux, xyzFlux)).expectNext("A").expectNext("B").expectNext("C").expectNext("X").expectNext("Y").expectNext("Z").verifyComplete();
	}

	@Test
	void testStringFluxConcatWith() {
		Flux<String> abcFlux = Flux.just("A", "B", "C");
		Flux<String> xyzFlux = Flux.just("X", "Y", "Z");
		StepVerifier.create(abcFlux.concatWith(xyzFlux)).expectNext("A").expectNext("B").expectNext("C").expectNext("X").expectNext("Y").expectNext("Z").verifyComplete();
	}

	@Test
	void testIntFluxCombineLatest() {
		Flux<Integer> flux1 = Flux.just(1, 2, 3);
		Flux<Integer> flux2 = Flux.just(4, 5, 6);
		Flux<Integer> combinedFlux = Flux.combineLatest(flux1, flux2, (a, b) -> a * b);
		StepVerifier.create(combinedFlux).expectNext(12).expectNext(15).expectNext(18).verifyComplete();
	}

	@Test
	void testStringFluxMerge() {
		Flux<String> abcFlux = Flux.just("A", "B", "C");
		Flux<String> xyzFlux = Flux.just("X", "Y", "Z");
		StepVerifier.create(Flux.merge(abcFlux, xyzFlux)).expectNext("A").expectNext("B").expectNext("C").expectNext("X").expectNext("Y").expectNext("Z").verifyComplete();
	}

	@Test
	void testStringFluxMergeSequential() {
		Flux<String> abcFlux = Flux.just("A", "B", "C");
		Flux<String> xyzFlux = Flux.just("X", "Y", "Z");
		StepVerifier.create(Flux.mergeSequential(abcFlux, xyzFlux)).expectNext("A").expectNext("B").expectNext("C").expectNext("X").expectNext("Y").expectNext("Z").verifyComplete();
	}

	@Test
	void testStringFluxMergeDelayError() {
		Flux<String> abcFlux = Flux.just("A", "B", "C");
		Flux<String> xyzFlux = Flux.just("X", "Y", "Z");
		StepVerifier.create(Flux.mergeDelayError(1, abcFlux, xyzFlux)).expectNext("A").expectNext("B").expectNext("C").expectNext("X").expectNext("Y").expectNext("Z").verifyComplete();
	}

	@Test
	void testStringFluxMergeWith() {
		Flux<String> abcFlux = Flux.just("A", "B", "C");
		Flux<String> xyzFlux = Flux.just("X", "Y", "Z");
		StepVerifier.create(abcFlux.mergeWith(xyzFlux)).expectNext("A").expectNext("B").expectNext("C").expectNext("X").expectNext("Y").expectNext("Z").verifyComplete();
	}

	@Test
	void testIntFluxZip() {
		Flux<Integer> flux1 = Flux.just(1, 2);
		Flux<Integer> flux2 = Flux.just(4, 5, 6);
		Flux<Integer> combinedFlux = Flux.zip(flux1, flux2, (a, b) -> a * b);
		StepVerifier.create(combinedFlux).expectNext(4).expectNext(10).verifyComplete();
	}

	@Test
	void testIntFluxZipWith() {
		Flux<Integer> flux1 = Flux.just(1, 2);
		Flux<Integer> flux2 = Flux.just(4, 5, 6);
		Flux<Integer> combinedFlux = flux1.zipWith(flux2, (a, b) -> a * b);
		StepVerifier.create(combinedFlux).expectNext(4).expectNext(10).verifyComplete();
	}
}

/*
 * Info came from:
 * https://www.baeldung.com/reactor-combine-streams
 */