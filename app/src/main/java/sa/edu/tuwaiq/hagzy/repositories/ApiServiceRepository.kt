package sa.edu.tuwaiq.hagzy.repositories

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sa.edu.tuwaiq.hagzy.api.FlickerApi
import com.google.gson.GsonBuilder

import com.google.gson.Gson





private const val BASE_URL = "https://api.flickr.com"
const val SHARED_PREF_FILE = "LatLong"
const val LATE_KEY = "latitude"
const val LONG_KEY = "longitude"

class ApiServiceRepository(val context: Context) {
    private val sharedPref = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    //To Solve use jsonreader.setlenient(true) to accept malformed json at line 1 column 1 path $

//    private var gson = GsonBuilder()
//        .setLenient()
//        .create()
//
//    private var retrofitService = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()


    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitApi =retrofitService.create(FlickerApi::class.java)
     // this request gets the photos from the flickr api with it's latitude and longitude

    //TODO the string lat and long might be null, Check how to resolve this
    //TODO give default lang and late
    suspend fun getPhotos() = retrofitApi.getPhotos(sharedPref.getString(
         LATE_KEY, "")!!,sharedPref.getString(LONG_KEY, "")!!) // lat:Latitude,lon:Longitude

//--------------------------------------------//

    // to initialize and get the repository we use the companion object
    //singleton (single object)
    companion object{
        private var instance:ApiServiceRepository?= null

        fun init (context: Context){
            if(instance == null)
                instance = ApiServiceRepository(context)
        }
        fun get(): ApiServiceRepository{
            return instance?: throw  Exception("ApiServiceRepository must be initialized")
        }
    }

}