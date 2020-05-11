package com.semashko.maps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlinx.android.synthetic.main.activity_maps.*

private const val POINTS_KEY = "POINTS_KEY"

class MapsActivity : AppCompatActivity() {

    private var points: ArrayList<com.semashko.provider.Point>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAP_KEY);
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_maps)

        points = intent.getParcelableArrayListExtra(POINTS_KEY)

        initMap()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun initMap() {
        mapView.map.move(
            CameraPosition(Point(53.6688, 23.8223), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
    }

    companion object {
        fun startActivity(context: Context, points: ArrayList<com.semashko.provider.Point>? = null) {
            val intent = Intent(context, MapsActivity::class.java).apply {
                putParcelableArrayListExtra(POINTS_KEY, points)
            }

            context.startActivity(intent)
        }
    }
}
