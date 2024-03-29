package edu.hniu.garbagestyle.api;

import static edu.hniu.garbagestyle.utils.Constant.KEY;


import edu.hniu.garbagestyle.model.GetDiscernResultResponse;
import edu.hniu.garbagestyle.model.GetTokenResponse;
import edu.hniu.garbagestyle.model.TrashNewsResponse;
import edu.hniu.garbagestyle.model.TrashResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * API接口
 *
 */
public interface ApiService {

    /**
     * 垃圾分类
     * @param word 物品名
     * @return TrashResponse 结果实体
     */
    @GET("/txapi/lajifenlei/index?key=" + KEY)
    Observable<TrashResponse> searchGoods(@Query("word") String word);

    /**
     * 获取鉴权认证Token
     * @param grant_type 类型
     * @param client_id API Key
     * @param client_secret Secret Key
     * @return GetTokenResponse
     */
    @FormUrlEncoded
    @POST("/oauth/2.0/token")
    Observable<GetTokenResponse> getToken(@Field("grant_type") String grant_type,
                                          @Field("client_id") String client_id,
                                          @Field("client_secret") String client_secret);

    /**
     * 获取图像识别结果
     * @param accessToken 获取鉴权认证Token
     * @param image 图片base64
     * @param url 网络图片Url
     * @return JsonObject
     */
    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v2/advanced_general")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<GetDiscernResultResponse> getDiscernResult(@Field("access_token") String accessToken,
                                                          @Field("image") String image,
                                                          @Field("url") String url);

    /**
     * 垃圾分类新闻
     * @param num 数量
     * @return TrashNewsResponse 结果实体
     */
    @GET("/lajifenleinews/index?key=" + KEY)
    Observable<TrashNewsResponse> getTrashNews(@Query("num") Integer num);


}
