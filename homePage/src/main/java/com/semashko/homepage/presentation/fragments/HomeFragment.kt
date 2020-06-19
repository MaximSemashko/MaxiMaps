package com.semashko.homepage.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.semashko.extensions.constants.EMPTY
import com.semashko.extensions.gone
import com.semashko.extensions.toEditable
import com.semashko.extensions.utils.EmptyTextWatcher
import com.semashko.extensions.visible
import com.semashko.homepage.R
import com.semashko.homepage.data.api.RealDataApi
import com.semashko.homepage.presentation.HomeUiState
import com.semashko.homepage.presentation.adapters.AttractionsAdapter
import com.semashko.homepage.presentation.adapters.MansionsAdapter
import com.semashko.homepage.presentation.adapters.TouristsRoutesAdapter
import com.semashko.homepage.presentation.viewmodels.HomeViewModel
import com.semashko.provider.BaseItemDecoration
import com.semashko.provider.Point
import com.semashko.provider.models.detailsPage.Reviews
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.HomeModel
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import com.semashko.provider.preferences.IUserInfoPreferences
import com.semashko.searchfragment.presentation.fragments.SearchFragment
import com.semashko.seealldetailspage.presentation.fragments.SeeAllFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.popular_places.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.concurrent.thread
import kotlin.math.abs

class HomeFragment : Fragment(R.layout.home_fragment), KoinComponent {

    private val viewModel: HomeViewModel by lifecycleScope.viewModel(this)

    private val userInfoPreferences: IUserInfoPreferences by inject()

    private lateinit var touristsRoutesAdapter: TouristsRoutesAdapter
    private lateinit var attractionsAdapter: AttractionsAdapter
    private lateinit var mansionsAdapter: MansionsAdapter

    private val touristsRoutesList = ArrayList<TouristsRoutes>()
    private val attractionsList = ArrayList<Attractions>()
    private val mansionsList = ArrayList<Mansions>()

    private val textWatcher: TextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length != null && s.length >= 3) {
                openSearchFragment(s.toString())
            }
        }
    }

    val touristsRoutesRealList = ArrayList<TouristsRoutes>()

    private fun initRealRoutes() {
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "Дорога памяти",
//                type = "Автобусно-пешеходная",
//                description = "Реконструкция событий на форте № 2 в д.Наумовичи",
//                points = listOf(
//                    Point(
//                        latitude = 53.7192482,
//                        longitude = 23.7246548
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Не очень понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Макс",
//                        stars = 3f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Понравилось, спасибо за маршрут",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 4f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Патриотический маршрут! 5+!",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 5f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://globus.tut.by/naumovichi/fort012_b0408.jpg",
//                    "https://radzima.org/images/pamatniki/2502/hrhrnavu03-01.jpg",
//                    "https://lh3.googleusercontent.com/proxy/SsiPzoe0cIDkcC5_KTy8W_x_V0oMIUsSXrh2smvJG0DQsJvj4ngZrRrKlozabgnPUA32yzbO3aPReLilb6oAlmXnlFeKE_qd5pRFhSrzng"
//                ),
//                distance = "41 километр",
//                duration = "40 минут"
//            )
//            TouristsRoutes(
//                name = "Привлекательный маршрут Гродно-Сапоцкин",
//                type = "Автобусно-пешеходная",
//                description = "маршурт в деревню Сапоцкин",
//                points = listOf(
//                    Point(
//                        latitude = 53.831833,
//                        longitude = 23.6558308
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Не понравилось от слова совсем!",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Макс",
//                        stars = 1f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Довольно странное место",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 3f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Мне понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 4f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://lh3.googleusercontent.com/proxy/fp-0P4C5XjR5S0SVTELwx8vGKNTmduDjGV9lsMu27YMncXrbdHOd2WHFeClMljqs3hHc4Bm-ySbV7XBnDiaXZN5xrqVhh4NDw3Ua7KsZwiNS",
//                    "https://souzveche.ru/upload/medialibrary/9b2/sv16_1210_usadbanew_110mm.jpg",
//                    "https://planetabelarus.by/upload/iblock/2ff/2ffcb9d9aa5d3cc2d7cb2eb9914444b7.jpg"
//                ),
//                distance = "25 километров",
//                duration = "1 час"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "У нас есть наследие от наших предков на протяжении веков",
//                type = "Автобусно-пешеходная",
//                description = "маршурт в деревню Одельск",
//                points = listOf(
//                    Point(
//                        latitude = 53.40414699999999,
//                        longitude = 23.7632832
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Very interesting place to visit!",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 4f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Патриотично!",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 4f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Мне понравилось, не рекомендую!",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 2f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://lh3.googleusercontent.com/proxy/U_wtXDj_plZWTctzUxfck82TNrbSrZHV2ACapHphVUXJfmHDe5xJ1Udf-apjSp2dClZzbeM7KLLCn-YtiziUS2Hf0Ko3tqH-8sjnKXWj7OjL-AM",
//                    "https://lh3.googleusercontent.com/proxy/Km-KU3mHE5rAZckIaHOoFCwyiJNKi9b03I38SvVO6rsQuwyDRaPrnAsyKAgQVcR00VeOgxrrjkVi1iOXkyFUTHzlckkgbPK67iacGLJNOOj27PQ",
//                    "https://lh3.googleusercontent.com/proxy/U_wtXDj_plZWTctzUxfck82TNrbSrZHV2ACapHphVUXJfmHDe5xJ1Udf-apjSp2dClZzbeM7KLLCn-YtiziUS2Hf0Ko3tqH-8sjnKXWj7OjL-AM"
//                ),
//                distance = "45 километров",
//                duration = "1 час 40 минут"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "Поречье",
//                type = "Автобусно-пешеходная",
//                description = "маршурт по аг.Поречье для отдыхающих в санаториях",
//                points = listOf(
//                    Point(
//                        latitude = 53.89173839999999,
//                        longitude = 24.1411871
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Oh my god! Nice",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Nice place to visit",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Visit it with your friends",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 5f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://www.multitour.ru/files/imgs/hotel_4879_62383_korpus_6.jpg",
//                    "https://globus.tut.by/poreche_gr/vokzal506_d118.jpg",
//                    "https://grodnorik.gov.by/uploads/files/materialy/turizm/Porechie/porechie5.jpg"
//                ),
//                distance = "40 километров",
//                duration = "1 час"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "Дорога памяти",
//                type = "Автобусно-пешеходная",
//                description = "Историко-туристический маршрут",
//                points = listOf(
//                    Point(
//                        latitude = 53.56498389999999,
//                        longitude = 23.8713339
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Не рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 2f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Не рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 3f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Не рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 3f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://lh3.googleusercontent.com/proxy/ue_ylS3cCftCBfmyl7VCG9DAGLQ7fTuCo_ogyaTiEGfc4mZwXok6jpyq6mk6aVRIMnasSOpcnZjYeq1X1jxRIX9X32SS61dCKDIhgrvWIpiPvpE",
//                    "https://grodroo.by/wp-content/uploads/2018/04/koptjovka-du-512x384.jpg",
//                    "https://www.holiday.by/files/houses/thumbnails/houses_gallery_fullsize/5250/fd2a6c9fd80fa1c9813be3d792e56694f62bb509.jpeg"
//                ),
//                distance = "300 метров",
//                duration = "1 час 30 минут"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "Августовский канал – жемчужина Принеманского края",
//                type = "Автобусно-пешеходная",
//                description = "Прогулка по августовскому каналу",
//                points = listOf(
//                    Point(
//                        latitude = 53.8918899,
//                        longitude = 23.3386028
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "100 из 10",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Очень понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Настоятельно рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 5f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://trofei.by/images/wysiwyg/2016-07-27/gdbWMo2mWdDP38GW.jpg",
//                    "https://34travel.me/media/posts/59241fa8cdc7b-rsz-sluza-paniewo.jpg",
//                    "https://sportintour.by/wp-content/uploads/2016/12/001488_329093.jpg"
//                ),
//                distance = "35 километров",
//                duration = "3 часа"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "агр.Озеры-оз.Белое – кв.66 Озерского лесничества (ночевка) – оз.Белое – турбаза “Химик”",
//                type = "Водный эколого-туристический маршрут",
//                description = "Путешествие по озерам",
//                points = listOf(
//                    Point(
//                        latitude = 53.7229098,
//                        longitude = 24.17972
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "100 из 10",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Очень понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Настоятельно рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 5f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://planetabelarus.by/upload/resize_cache/uf/3c7/1330_887_18e21fe612b4afb807a26ecc22279a1d9/3c7df9a5aee31c85993e1e3a5512bbf9.jpg",
//                    "https://grodnonews.by/upload/resize_cache/iblock/16e/924_560_2/16ec766c63b32613a6b2b57bc7a21634.JPG",
//                    "https://lh3.googleusercontent.com/proxy/497CTDAkMymGn6tFk5NLTnYzregnWpEQ2Vi0-cbSsw-KBwYpSvWI_KOEs5gBKIF5DmuUf5RjDSy3ASiB4XBJn1-kJTsKGf-U5QlJZ8yPDhhwFVDb9fOky5eE4xFWQAs"
//                ),
//                distance = "10 километров",
//                duration = "4 часа"
//            )
//        )
//
//        //////////////////////////////////////////
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "“Августовский шлях” : Августовский канал – шлюз “Кужинец” – деревня Лесная – деревня Соничи – шлюз “Немново” – деревня Немново",
//                type = "велосипедный",
//                description = "Велосипедная прогулка",
//                points = listOf(
//                    Point(
//                        latitude = 53.8918899,
//                        longitude = 23.3386028
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "100 из 10",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Очень понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Настоятельно рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 5f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://planetabelarus.by/upload/resize_cache/uf/3c7/1330_887_18e21fe612b4afb807a26ecc22279a1d9/3c7df9a5aee31c85993e1e3a5512bbf9.jpg",
//                    "https://grodnonews.by/upload/resize_cache/iblock/16e/924_560_2/16ec766c63b32613a6b2b57bc7a21634.JPG",
//                    "https://lh3.googleusercontent.com/proxy/497CTDAkMymGn6tFk5NLTnYzregnWpEQ2Vi0-cbSsw-KBwYpSvWI_KOEs5gBKIF5DmuUf5RjDSy3ASiB4XBJn1-kJTsKGf-U5QlJZ8yPDhhwFVDb9fOky5eE4xFWQAs"
//                ),
//                distance = "25 километров",
//                duration = "4 часа"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "«Линия Молотова» : шлюз «Домбровка» – доты – деревня Песчаны – деревня Радзивилки – городской поселок Сопоцкин – шлюз «Домбровка»",
//                type = "велосипедный",
//                description = "Велосипедная прогулка",
//                points = listOf(
//                    Point(
//                        latitude = 53.84955160000001,
//                        longitude = 23.7006128
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "100 из 10",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Очень понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Настоятельно рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 5f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://planetabelarus.by/upload/resize_cache/iblock/c1c/1330_887_18e21fe612b4afb807a26ecc22279a1d9/c1cf313450f1a56b2a9a155fd2b462b5.jpg",
//                    "https://lh3.googleusercontent.com/proxy/uRaM5kvgqlSDL_iCGxsqVPh2Q6lHp7waDTzTCHRzAArgEj5juW5idd48gg6pQ7w0LVaSqP_OPGo3N-5j0GILQSVZaOOCIKp7Ch4NM9eOozQ38MX8xGU",
//                    "https://excursio.com/upload/excursions_photo/598693/268790_p_335x226_e09b7b343f601a4419677a686a839152.jpg"
//                ),
//                distance = "12 километров",
//                duration = "3 часа"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "Немновский",
//                type = "пеший",
//                description = "деревня Немново - шлюз «Немново» – плотина «Куркуль» - деревня «Немново»",
//                points = listOf(
//                    Point(
//                        latitude = 53.8689756,
//                        longitude = 23.757093
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Не понравилось, слишком долго ходили",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 1f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Очень понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 4f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Настоятельно не рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 1f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://www.komandirovka.ru/upload/iblock/439/439e62fa0766ab4fd9b3bf7c9220ea37.jpg",
//                    "https://lh3.googleusercontent.com/proxy/B4QT6yjhlhGyu0Hbs8JF1S3QCZw1mxsfuZ9HLMzONhbhg8bAOhALvskRYtG-Fta-i88w9TSpQ29vK-aZWfgW2tyqtb3d7TZ6ssS_OaW_eMNwJnTJ3Rs9nf7Nj2U5x2PiM5qYUce2QxO-E71gzYBpp7TLdg",
//                    "https://natatnik.by/wp-content/uploads/2017/06/DSC08340_%D0%BD%D0%BE%D0%B2%D1%8B%D0%B9-%D1%80%D0%B0%D0%B7%D0%BC%D0%B5%D1%80-1.jpg"
//                ),
//                distance = "4 километра",
//                duration = "2 часа"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "Неманский",
//                type = "водный",
//                description = "город Гродно (старый мост) – река Неман – деревня Привалки",
//                points = listOf(
//                    Point(
//                        latitude = 53.9460443,
//                        longitude = 23.9218357
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Не понравилось, слишком долго ходили",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 2f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Очень понравилось",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 3f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Настоятельно не рекомендую",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 2f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://planetabelarus.by/upload/resize_cache/iblock/6eb/1330_887_18e21fe612b4afb807a26ecc22279a1d9/6eb5416dcd65d5208f4423c4e5793c07.jpg",
//                    "https://planetabelarus.by/upload/resize_cache/iblock/1db/1330_887_18e21fe612b4afb807a26ecc22279a1d9/1dbd29f04122852e6f21394de74ac856.jpg"
//                ),
//                distance = "20 километров",
//                duration = "5 часов"
//            )
//        )
//
//        touristsRoutesRealList.add(
//            TouristsRoutes(
//                name = "Сосновая слезка",
//                type = "пеший",
//                description = "деревня Немново – деревня Ятвезь",
//                points = listOf(
//                    Point(
//                        latitude = 53.8689756,
//                        longitude = 23.757093
//                    ),
//                    Point(
//                        latitude = 53.17265339999999,
//                        longitude = 24.394212
//                    )
//                ),
//                reviews = listOf(
//                    Reviews(
//                        text = "Интересный маршрут",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Max",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/3Fm8uWkAAFcqeGfHUXjsQXlRcXW2?alt=media"
//                    ),
//                    Reviews(
//                        text = "Спасибо!",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Ольга",
//                        stars = 5f,
//                        userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/XiKpSyVrB3P1vpqnfuApj5mXgV53?alt=media"
//                    ),
//                    Reviews(
//                        text = "Интересное место!",
//                        timestamp = System.currentTimeMillis(),
//                        userName = "Иван",
//                        stars = 4f,
//                        userImageUrl = "https://24smi.org/public/media/celebrity/2020/02/19/6gyrvhduuj1j-ivan-kurgalin.jpg"
//                    )
//                ),
//                imagesUrls = listOf(
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQu4B04eAu1J3MxSVjwzVozmI-E7kp5y3hgZWZN2wwne3YbIU7T&usqp=CAU",
//                    "https://i.ytimg.com/vi/EbwDx8FNu4M/maxresdefault.jpg"
//                ),
//                distance = "3 километра",
//                duration = "1 час"
//            )
//        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()
        initSeeAllButtons()
        initSearchInputLayout()
        initPersonImageView()

        initRealRoutes()

        thread {
            touristsRoutesRealList.forEach {
                RealDataApi().addTouristRoute(it)
            }
        }
        viewModel.homeData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeUiState.Loading -> progressBar.visible()
                is HomeUiState.Success -> {
                    progressBar.gone()

                    it.homeModel.routes?.let { items -> touristsRoutesAdapter.setItems(items) }
                    it.homeModel.mansions?.let { items -> mansionsAdapter.setItems(items) }
                    it.homeModel.attractions?.let { items -> attractionsAdapter.setItems(items) }

                    initList(it.homeModel)
                }
                is HomeUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadModel()
    }

    private fun initPersonImageView() {
        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/${userInfoPreferences.localId}?alt=media")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(personImageView)
    }

    private fun initList(homeModel: HomeModel?) {
        homeModel?.routes?.let { touristsRoutesList.addAll(it) }
        homeModel?.mansions?.let { mansionsList.addAll(it) }
        homeModel?.attractions?.let { attractionsList.addAll(it) }
    }

    private fun initSearchInputLayout() {
        searchInputLayout.editText?.addTextChangedListener(textWatcher)
    }

    private fun initToolbar() {
        appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when {
                abs(verticalOffset) - appBarLayout.totalScrollRange == 0 -> view?.findViewById<View>(
                    R.id.action_search
                )?.visible()
                else -> view?.findViewById<View>(R.id.action_search)?.gone()
            }
        })

        with(toolbar) {
            inflateMenu(R.menu.toolbar_menu)

            setOnMenuItemClickListener {
                openSearchFragment(null)

                false
            }
        }
    }

    private fun initSeeAllButtons() {
        seeAllAttractionsView.text = Html.fromHtml("<u>${getString(R.string.see_all)}</>")
        seeAllMansionsView.text = Html.fromHtml("<u>${getString(R.string.see_all)}</>")
        seeAllTouristsRoutesView.text = Html.fromHtml("<u>${getString(R.string.see_all)}</>")

        seeAllTouristsRoutesView.setOnClickListener {
            openSeeAllDetailsPage(HomeModel(routes = touristsRoutesList))
        }

        seeAllAttractionsView.setOnClickListener {
            openSeeAllDetailsPage(HomeModel(attractions = attractionsList))
        }

        seeAllMansionsView.setOnClickListener {
            openSeeAllDetailsPage(HomeModel(mansions = mansionsList))
        }
    }

    private fun openSearchFragment(searchInput: String?) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.homeContainer, SearchFragment.newInstance(searchInput))
            ?.addToBackStack(null)
            ?.commit()

        searchInputLayout.editText?.text = EMPTY.toEditable()
    }

    private fun openSeeAllDetailsPage(homeModel: HomeModel) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.homeContainer, SeeAllFragment.newInstance(homeModel))
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun initRecyclerView() {
        touristsRoutesAdapter = TouristsRoutesAdapter(activity)
        mansionsAdapter = MansionsAdapter(activity)
        attractionsAdapter = AttractionsAdapter(activity)

        touristsRoutesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = touristsRoutesAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }

        mansionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mansionsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }

        attractionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = attractionsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }
    }
}
