package com.example.applemarket

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataList = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 원본 준비
        dataList.add(Item(R.drawable.sample1, "산지 한달된 선풍기 팝니다", "이사가서 필요가 없어졌어요 급하게 내놓습니다", "대현동", 1000,"서울 서대문구 창천동", 13, 25, false))
        dataList.add(Item(R.drawable.sample2, "김치냉장고", "이사로 인해 내놔요", "안마담", 20000, "인천 계양구 귤현동", 8, 28, false))
        dataList.add(Item(R.drawable.sample3, "샤넬 카드지갑", "고퀄지갑이구요\n사용감이 있어서 싸게 내어둡니다", "코코유", 10000, "수성구 범어동", 23, 5, false))
        dataList.add(Item(R.drawable.sample4, "금고", "금고\n떼서 가져가야함\n대우월드마크센텀\n미국이주관계로 싸게 팝니다", "Nicole", 10000, "해운대구 우제2동", 14, 17, false))
        dataList.add(Item(R.drawable.sample5, "갤럭시Z플립3 팝니다", "갤럭시 Z플립3 그린 팝니다\n항시 케이스 씌워서 썻고 필름 한장 챙겨드립니다\n화면에 살짝 스크래치난거 말고 크게 이상은없습니다!", "절명", 150000, "연제구 연산 제8동", 22, 9, false))
        dataList.add(Item(R.drawable.sample6, "프라다 복조리백", "까임 오염없고 상태 깨끗합니다\n정품 여부 모름", "미니멀하게", 50000, "수원시 영통구 원천동", 25, 16, false))
        dataList.add(Item(R.drawable.sample7, "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장",  "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\n2. 장소: 북구 블루마시티, 32-33층\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\n6. 고기 굽기 가능\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\n8. 층간소음 주의 부탁드립니다.\n9. 방3개, 화장실3개, 비데 3개\n10. 저희 집안이 쓰는 별장입니다.", "굿리치", 150000, "남구 옥동", 142, 54, false))
        dataList.add(Item(R.drawable.sample8, "샤넬 탑핸들 가방", "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백 !\n\n색상 : 블랙\n사이즈 : 25.5cm * 17.5cm * 8cm\n구성 : 본품더스트\n\n급하게 돈이 필요해서 팝니다 ㅠ ㅠ", "난쉽", 180000, "동래구 온천 제2동", 31, 7, false))
        dataList.add(Item(R.drawable.sample9, "4행정 엔진분무기 판매합니다.", "3년전에 사서 한번 사용하고 그대로 둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내 놓습니다. 중고라 반품은 어렵습니다.", "알뜰한", 30000, "원주시 명륜2동", 7, 28, false))
        dataList.add(Item(R.drawable.sample10,"셀린느 버킷 가방", "22년 신세계 대전 구매입니당\n셀린느 버킷백\n구매해서 몇번사용했어요\n까짐 스크래치 없습니다.\n타지역에서 보내는거라 택배로 진행합니당!\"", "똑태현", 190000, "중구 동화동", 40, 6, false))
        // R.drawable.sample1 : 이미지 파일의 주소(Int) 값

        val adapter = GoodsAdapter(dataList)
        binding.itemRv.adapter = adapter
        binding.itemRv.layoutManager = LinearLayoutManager(this)

        binding.ivBell.setOnClickListener {
            popupNotification()
        }

        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        fadeIn.fillAfter = true     // 애니메이션 후 상태를 유지
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true

        binding.itemRv.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.itemRv.canScrollVertically(-1)  // Scroll up & top
                    && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.fabToTop.startAnimation(fadeOut)
                    binding.fabToTop.visibility = View.GONE
                    isTop = true
                } else {  // Scroll down....
                    if(isTop) {
                        binding.fabToTop.visibility = View.VISIBLE
                        binding.fabToTop.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })

        binding.fabToTop.setOnClickListener {
            binding.itemRv.smoothScrollToPosition(0)
        }

        // 알림 권한이 없다면, 사용자에게 권한 요청 (안드로이드 12 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                // 알림 권한이 없다면, 사용자에게 권한 요청
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
        }

        adapter.itemClick = object : GoodsAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val item: Item = dataList[position]
                // DetailActivity로 이동
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("item", item)
                startActivity(intent)
            }
        }

        adapter.itemLongClick = object : GoodsAdapter.ItemLongClick {
            override fun onLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@MainActivity)
                    .setIcon(R.drawable.ic_chats)
                    .setTitle("상품 삭제")
                    .setMessage("정말로 삭제하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        dataList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        }

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_chats)
            .setTitle("종료")
            .setMessage("정말 종료하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                super.onBackPressed()
                finish()
            }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun popupNotification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 26 버전 이상
            val channelId = "AppleMarket"
            val channelName = "My Channel AppleMarket"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // 채널에 다양한 정보 설정
                description = "Welcome to AppleMarket"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            // 26 버전 이하
            builder = NotificationCompat.Builder(this)
        }

        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("사과마켓 알림입니다.")
            setContentText("새 알림이 도착했습니다.")
        }

        manager.notify(11, builder.build())
    }
}