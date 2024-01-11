package com.example.applemarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarket.databinding.ActivityDetailBinding
import java.text.NumberFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<Item>("item")

        item?.let {
            binding.ivItemDetail.setImageResource(it.image)
            binding.ivProfileDetail.setImageResource(R.drawable.ic_profile)
            binding.tvSellerDetail.text = it.seller
            binding.tvAddressDetail.text = it.address
            binding.tvNameDetail.text = it.name
            binding.tvDescriptionDetail.text = it.description

            val priceFormat = NumberFormat.getNumberInstance().format(it.price)
            binding.tvPriceDetail.text = "${priceFormat}원"
        }

        // 버튼을 누르면 상세화면 종료되고 메인화면으로 돌아감
        binding.ivBackDetail.setOnClickListener {
            finish()
        }
    }
}