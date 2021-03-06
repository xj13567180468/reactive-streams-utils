/************************************************************************
 * Licensed under Public Domain (CC0)                                    *
 *                                                                       *
 * To the extent possible under law, the person who associated CC0 with  *
 * this code has waived all copyright and related or neighboring         *
 * rights to this code.                                                  *
 *                                                                       *
 * You should have received a copy of the CC0 legalcode along with this  *
 * work. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.*
 ************************************************************************/

package org.reactivestreams.utils.tck;

import org.reactivestreams.utils.ReactiveStreams;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.stream.LongStream;

import static org.testng.Assert.assertEquals;

public class OfManyStageVerification extends AbstractStageVerification {

  OfManyStageVerification(ReactiveStreamsTck.VerificationDeps deps) {
    super(deps);
  }

  @Test
  public void iterableStageShouldEmitManyElements() {
    assertEquals(await(
        ReactiveStreams.of("a", "b", "c")
            .toList()
            .build(engine)
    ), Arrays.asList("a", "b", "c"));
  }

  @Override
  List<Object> reactiveStreamsTckVerifiers() {
    return Collections.singletonList(new PublisherVerification());
  }

  class PublisherVerification extends StagePublisherVerification<Long> {
    @Override
    public Flow.Publisher<Long> createFlowPublisher(long elements) {
      return ReactiveStreams.fromIterable(
          () -> LongStream.rangeClosed(1, elements).boxed().iterator()
      ).build(engine);
    }
  }


}
