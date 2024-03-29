package edu.hniu.garbagestyle.contract;

import android.annotation.SuppressLint;




import edu.hniu.garbagestyle.api.ApiService;
import edu.hniu.garbagestyle.model.TrashResponse;
import edu.hniu.mvplibrary.base.BasePresenter;
import edu.hniu.mvplibrary.base.BaseView;
import edu.hniu.mvplibrary.network.NetworkApi;
import edu.hniu.mvplibrary.network.observer.BaseObserver;

/**
 * 手动文字输入页面访问网络
 *
 */
public class TextContract {

    public static class TextPresenter extends BasePresenter<TextView> {
        /**
         * 搜索物品
         *
         * @param word 物品名
         */
        @SuppressLint("CheckResult")
        public void searchGoods(String word) {
            ApiService service = NetworkApi.createService(ApiService.class,0);
            service.searchGoods(word).compose(NetworkApi.applySchedulers(new BaseObserver<TrashResponse>() {
                @Override
                public void onSuccess(TrashResponse groupResponse) {
                    if (getView() != null) {
                        getView().getSearchResponse(groupResponse);
                    }
                }

                @Override
                public void onFailure(Throwable e) {
                    if (getView() != null) {
                        getView().getSearchResponseFailed(e);
                    }
                }
            }));
        }
    }

    public interface TextView extends BaseView {
        /**
         * 搜索物品返回
         *
         * @param response
         */
        void getSearchResponse(TrashResponse response);

        /**
         * 搜索物品异常返回
         *
         * @param throwable
         */
        void getSearchResponseFailed(Throwable throwable);
    }
}
