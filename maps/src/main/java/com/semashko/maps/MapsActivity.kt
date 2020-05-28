package com.semashko.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.runtime.Error
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import kotlinx.android.synthetic.main.activity_maps.*

private const val POINTS_KEY = "POINTS_KEY"
private const val PERMISSION_ID = 66
private val GRODNO_POSITION = Point(53.6688, 23.8223)

class MapsActivity : AppCompatActivity(), DrivingSession.DrivingRouteListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var drivingRouter: DrivingRouter
    private lateinit var drivingSession: DrivingSession
    private lateinit var mapObjects: MapObjectCollection

    private var points: ArrayList<com.semashko.provider.Point>? = null

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.getLastLocation()
            //TODO
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAP_KEY)
        MapKitFactory.initialize(this)
        DirectionsFactory.initialize(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        points = intent.getParcelableArrayListExtra(POINTS_KEY)

        getLastLocation()
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

    override fun onDrivingRoutesError(error: Error) {
        val errorMessage =
            when (error) {
                is RemoteError -> getString(R.string.remote_error_message)
                is NetworkError -> getString(R.string.network_error_message)
                else -> getString(R.string.unknown_error_message)
            }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDrivingRoutes(routes: List<DrivingRoute>) {
        routes.forEach { route ->
            mapObjects.addPolyline(route.geometry)
        }
    }

    private fun initMap() {
        mapView.map.move(
            CameraPosition(GRODNO_POSITION, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )

        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        mapObjects = mapView.map.mapObjects.addCollection()

        submitRequest()
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
                //TODO
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        Log.i("TAG", location.latitude.toString() + "")
                        Log.i("TAG", location.longitude.toString() + "")
                        //TODO
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        with(LocationRequest()) {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 0
            fastestInterval = 0
            numUpdates = 1

            fusedLocationClient.requestLocationUpdates(
                this,
                locationCallback,
                Looper.myLooper()
            )
        }
    }

    private fun submitRequest() {
        //TODO
        val options = DrivingOptions()
        val requestPoints = java.util.ArrayList<RequestPoint>()

        requestPoints.add(
            RequestPoint(
                GRODNO_POSITION,
                RequestPointType.WAYPOINT,
                null
            )
        )
        drivingSession = drivingRouter.requestRoutes(requestPoints, options, this)
    }

    companion object {
        fun startActivity(
            context: Context,
            points: ArrayList<com.semashko.provider.Point>? = null
        ) {
            val intent = Intent(context, MapsActivity::class.java).apply {
                putParcelableArrayListExtra(POINTS_KEY, points)
            }

            context.startActivity(intent)
        }
    }
}
