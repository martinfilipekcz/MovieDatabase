package com.example.martinfilipek.moviedatabase.mvp;

import com.example.martinfilipek.moviedatabase.App;
import com.example.martinfilipek.moviedatabase.dagger.component.PresenterComponent;
import com.example.martinfilipek.moviedatabase.utils.RxUtils;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;

/**
 * Created by Martin Filipek on 07.04.2018.
 */
public abstract class BasePresenter<V extends BaseView> implements Presenter<V> {

    private final PresenterComponent mPresenterComponent;
    private CompositeDisposable mCompositeDisposable;
    private V mView;

    public BasePresenter(){
        mPresenterComponent = App.getInstance()
                .getComponent()
                .presenterComponent();

        inject();
    }

    @Override
    public void onViewAttached(V view) {
        mView = view;
        mCompositeDisposable = RxUtils.newDisposableIfDisposed(mCompositeDisposable);
    }

    @Override
    public void onViewDetached() {
        RxUtils.clearDisposable(mCompositeDisposable);
        mView = null;
    }

    protected V getView(){
        return mView;
    }

    protected boolean isViewAttached(){
        return mView == null;
    }

    protected PresenterComponent getPresenterComponent(){
        return mPresenterComponent;
    }

    /**
     * Add a {@link Disposable} object into a {@link CompositeDisposable} list to avoid memory leaks
     * <p>
     * Once a presenter is detached its disposables are deleted
     *
     * @param disposable to add
     */
    protected void addToDisposables(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    /**
     * Subscribe a @Single observable that is emitted only once
     * <p>
     * Single service is unregistered automatically when one of its method is called
     *
     * @param singleObservable single observable to subscribe
     * @param singleObserver   consumer with onSuccess(), onError() and onSubscribe() methods
     * @param <T>              return type of onSuccess()
     */
    protected <T> void subscribeSingle(Single<T> singleObservable, SingleObserver<T> singleObserver) {
        singleObservable.compose(RxUtils.applySingleSchedulers()).subscribe(singleObserver);
    }

    protected <T> void subscribeSingle(Single<T> serviceSingle, Consumer<T> onSucces,
                                       Consumer<Throwable> onErrorConsumer){
        mCompositeDisposable.add(serviceSingle.compose(RxUtils.applySingleSchedulers())
                        .subscribe(onSucces, onErrorConsumer)
                );
    }

    protected <T> void subscribeObservable(Observable<T> serviceObservable, Consumer<T> onNextConsumer,
                                           Consumer<Throwable> onErrorConsumer, Action onCompleteAction) {
        mCompositeDisposable.add(serviceObservable.compose(RxUtils.applySchedulers())
                        .subscribe(onNextConsumer, onErrorConsumer, onCompleteAction)
                );
    }

    /**
     * Register service with given ID & subscribe to onNextConsumer, onErrorConsumer.
     * <p>
     * Service unregister in onCompleted action.
     *
     * @param serviceObservable service to subscribe
     * @param onNextConsumer    on next consumer
     * @param onErrorConsumer   on error consumer
     * @param <T>               return type of on next consumer
     */
    protected <T> void subscribeObservable(Observable<T> serviceObservable,
                                           Consumer<T> onNextConsumer, Consumer<Throwable> onErrorConsumer) {
        subscribeObservable(serviceObservable, onNextConsumer, onErrorConsumer, Functions.EMPTY_ACTION);
    }

    /**
     * Register service with given ID & subscribe to onNextConsumer.
     * On error is not handled.
     * <p>
     * Service unregister in onCompleted action.
     *
     * @param serviceObservable service to subscribe
     * @param onNextConsumer    on next consumer
     * @param <T>               return type of on next consumer
     */
    protected <T> void subscribeObservable(Observable<T> serviceObservable, Consumer<T> onNextConsumer) {
        subscribeObservable(serviceObservable, onNextConsumer, Functions.ON_ERROR_MISSING);
    }

    /**
     * Subscribe service with given ID & subscribe.
     * On error is not handled, on next is not handled.
     * <p>
     * Service unregister in onCompleted action.
     *
     * @param serviceObservable service to subscribe
     * @param <T>               return type of observable service
     */
    protected <T> void subscribeObservable(Observable<T> serviceObservable) {
        subscribeObservable(serviceObservable, Functions.emptyConsumer());
    }

    protected abstract void inject();
}
