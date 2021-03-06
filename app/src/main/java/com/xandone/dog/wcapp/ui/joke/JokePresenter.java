package com.xandone.dog.wcapp.ui.joke;


import com.xandone.dog.wcapp.api.CommonSubscriber;
import com.xandone.dog.wcapp.base.RxPresenter;
import com.xandone.dog.wcapp.model.DataManager;
import com.xandone.dog.wcapp.model.base.BaseResponse;
import com.xandone.dog.wcapp.model.bean.JokeBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: xandone
 * created on: 2019/3/13 9:26
 */

public class JokePresenter extends RxPresenter<JokeContact.View> implements JokeContact.Presenter {
    private DataManager dataManager;

    @Inject
    public JokePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getJokeList(int page, int count, String tag, final int mode) {
        Flowable<BaseResponse<List<JokeBean>>> result = dataManager.getJokeList(page, count, tag);
        addSubscrible(result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<BaseResponse<List<JokeBean>>>(view) {
                    @Override
                    public void onSuccess(BaseResponse<List<JokeBean>> jokeBean) {
                        if (mode == JokeContact.MODE_ONE) {
                            view.showContent(jokeBean.getData(), jokeBean.getTotal());
                        } else if (mode == JokeContact.MODE_MORE) {
                            view.showContentMore(jokeBean.getData(), jokeBean.getTotal());
                        }
                    }
                })
        );
    }

    @Override
    public void dealSearchJokes(int page, int count, String key, final int mode) {
        try {
            key = URLEncoder.encode(key, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Flowable<BaseResponse<List<JokeBean>>> result = dataManager.dealSearchJokes(page, count, key);
        addSubscrible(result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<BaseResponse<List<JokeBean>>>(view) {
                    @Override
                    public void onSuccess(BaseResponse<List<JokeBean>> jokeBean) {
                        if (mode == JokeContact.MODE_ONE) {
                            view.showContent(jokeBean.getData(), jokeBean.getTotal());
                        } else if (mode == JokeContact.MODE_MORE) {
                            view.showContentMore(jokeBean.getData(), jokeBean.getTotal());
                        }
                    }
                })
        );
    }

}
