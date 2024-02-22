package edu.hniu.garbagestyle.contract;

import android.annotation.SuppressLint;




import edu.hniu.garbagestyle.api.ApiService;
import edu.hniu.garbagestyle.model.TrashNewsResponse;
import edu.hniu.mvplibrary.base.BasePresenter;
import edu.hniu.mvplibrary.base.BaseView;
import edu.hniu.mvplibrary.network.NetworkApi;
import edu.hniu.mvplibrary.network.observer.BaseObserver;

/**
 * 主页面访问网络
 *
 */
public class MainContract {

    public static class MainPresenter extends BasePresenter<MainView> {
        /**
         * 垃圾分类新闻
         *
         * @param num 数量
         */
        @SuppressLint("CheckResult")
        public void getTrashNews(Integer num) {
            ApiService service = NetworkApi.createService(ApiService.class,0);
            service.getTrashNews(num).compose(NetworkApi.applySchedulers(new BaseObserver<TrashNewsResponse>() {
                @Override
                public void onSuccess(TrashNewsResponse trashNewsResponse) {
                    if (getView() != null) {
                        getView().getTrashNewsResponse(trashNewsResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getTrashNewsFailed(e);
                    }
                }
            }));
        }
    }

    public interface MainView extends BaseView {
        /**
         * 获取垃圾分类新闻返回
         *
         * @param response
         */
        void getTrashNewsResponse(TrashNewsResponse response);

        /**
         * 搜索物品异常返回
         *
         * @param throwable
         */
        void getTrashNewsFailed(Throwable throwable);
    }
}
