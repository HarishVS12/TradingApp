package com.trading.tradingapp.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomersAPI {

    //UserLogin Request

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginJSON> loginPost(@Field("username") String username , @Field("password") String password);

    @GET("app/customer/{id}")
    Call<List<GetCustomers>> getCustomers(@Path("id") int id);

    @FormUrlEncoded
    @PUT("app/customers/update/{id}")
    Call<UpdateCustomer> UpdateDetails(@Path("id") int id,
                              @Field("firstname") String firstName, @Field("lastname") String lastName,
                              @Field("username") String userName,@Field("brokername") String brokerName,
                              @Field("brokerid") int brokerId,@Field("panno") String panNo,
                              @Field("address") String address);
}
