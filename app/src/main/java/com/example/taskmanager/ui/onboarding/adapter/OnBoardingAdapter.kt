package com.example.taskmanager.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.ItemOnboardingBinding
import com.example.taskmanager.model.OnBoarding
import com.example.taskmanager.utils.loadImage

class OnBoardingAdapter(private val onClick: () -> Unit) :
    Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private val data = arrayListOf(
        OnBoarding(
            "Добавление задачь",
            "В данном приложение вы можете добавлять задачи с которыми вы бы хотели справиться",
            "https://assets.website-files.com/634681057b887c6f4830fae2/6367dd6185ecb40246a15a58_6259f61650a8c8088d4a7a8a_Android-Note-Apps.png"
        ),
        OnBoarding(
            "Отмечать выполненые задачи",
            "Помечать выполненые задачи что бы отслеживать ваши результаты",
            "https://softstribe.com/wp-content/uploads/15-best-task-manager-apps-for-android-in-2019.png"
        ),
        OnBoarding(
            "Удаление задачь",
            "Так же вы можете удалять задачи которые вы передумали выполнять",
            "https://www.droid-life.com/wp-content/uploads/2023/03/Task-Killer-980x653.jpg"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            ItemOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    inner class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        ViewHolder(binding.root) {
        fun bind(onBoarding: OnBoarding) = with(binding) {
            tvTitle.text = onBoarding.title
            tvDesc.text = onBoarding.desc
            onBoarding.image?.let { imgBoard.loadImage(it) }
            btnStart.isVisible = adapterPosition == data.lastIndex
            tvSkip.isVisible = adapterPosition != data.lastIndex
            btnStart.setOnClickListener {
                onClick()
            }
            tvSkip.setOnClickListener {
                onClick()
            }
        }
    }
}