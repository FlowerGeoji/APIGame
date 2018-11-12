package app.geoji.flower.apigameandroidtester.mock;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class LiveCast {
    private static LiveCast instance;

    synchronized public static LiveCast getInstance() {
        if (instance == null) {
            instance = new LiveCast();
        }
        return instance;
    }

    private final Subject<String> subject = PublishSubject.create();

    private LiveCast() {

    }

    public void sendMetadata(String metadata) {
        subject.onNext(metadata);
    }

    public Observable<String> getMetadata() {
        return subject.flatMap(metadata -> Observable.just(metadata).delaySubscription(0, TimeUnit.MILLISECONDS));
    }
}
