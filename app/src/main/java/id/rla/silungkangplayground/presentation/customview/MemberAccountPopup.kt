package id.rla.silungkangplayground.presentation.customview

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListPopupWindow
import android.widget.PopupWindow
import androidx.recyclerview.widget.DividerItemDecoration
import id.rla.silungkangplayground.databinding.MainpageAccountPopupBinding
import id.rla.silungkangplayground.domain.model.MemberAccount
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.MemberAccountAdapter
import id.rla.silungkangplayground.presentation.util.toDp
import java.lang.reflect.Member

class MemberAccountPopup(
    layoutInflater: LayoutInflater,
    logoutCallback:() -> Unit,
    changeMemberAccountCallback:(String) -> Unit,
    listMemberAccounts:List<MemberAccount> = emptyList()
) {

    private val onClickItem = { memberId:String ->
        dismissPopup()
        changeMemberAccountCallback(memberId)
    }


    private val binding:MainpageAccountPopupBinding = MainpageAccountPopupBinding.inflate(layoutInflater).also {
        it.rvMemberAccount.adapter = MemberAccountAdapter(
            listMemberAccounts,
            onClickItem = onClickItem
        )

        it.rvMemberAccount.addItemDecoration(
            DividerItemDecoration(layoutInflater.context, DividerItemDecoration.VERTICAL)
        )
        it.actvLogout.setOnClickListener {
            dismissPopup()
            logoutCallback()
        }
    }

    fun updateListMemberAccount(list:List<MemberAccount>){
        binding.rvMemberAccount.swapAdapter(
            MemberAccountAdapter(list, onClickItem = onClickItem), false
        )
        if (isPopUpShown){
            popUpWindow.update()
        }
    }

    private val popUpWindow:PopupWindow by lazy {
        PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        ).also { it.isOutsideTouchable = true }
    }

    var isPopUpShown:Boolean = false
        private set

    fun showPopup(anchorView: View){
        if (!isPopUpShown){
            val displayMetrics = anchorView
                .context
                .resources
                .displayMetrics


            popUpWindow.showAsDropDown(
                anchorView,
                -(22.toDp(displayMetrics)),
                4.toDp(displayMetrics),
            )
            isPopUpShown = true
        }
    }

    fun dismissPopup(){
        if (isPopUpShown){
            popUpWindow.dismiss()
            isPopUpShown = false
        }
    }




}