package com.rn1.puffren.ext

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.data.EntryFrom
import com.rn1.puffren.data.ItemPackage
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.User
import com.rn1.puffren.factory.*
import com.rn1.puffren.ui.achievement.AchievementTypeFilter
import com.rn1.puffren.ui.product.ProdTypeFilter

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(user: User?): UserViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return UserViewModelFactory(repository, user)
}

fun Fragment.getVmFactory(prodTypeFilter: ProdTypeFilter): ProdItemViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return ProdItemViewModelFactory(repository, prodTypeFilter)
}

fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return ProductViewModelFactory(repository, product)
}

fun Fragment.getVmFactory(product: Product?, itemPackage: ItemPackage?): CartViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return CartViewModelFactory(repository, product, itemPackage)
}

fun Fragment.getVmFactory(string: String): StringViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return StringViewModelFactory(repository, string)
}

fun Fragment.getVmFactory(achievementTypeFilter: AchievementTypeFilter): AchievementViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return AchievementViewModelFactory(repository, achievementTypeFilter)
}

fun Fragment.getVmFactory(entryFrom: EntryFrom): EntryViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return EntryViewModelFactory(repository, entryFrom)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.loadingDialog(cancelable: Boolean = true): Dialog =
    Dialog(requireActivity()).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(initLoadingProgress())
        setCanceledOnTouchOutside(false)
        setCancelable(cancelable)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

fun Fragment.messageDialog(message: String = "", cancelable: Boolean = true): Dialog {
    val view = initMessageDialogView().apply {
        findViewById<TextView>(R.id.text_message_content).text = message
    }

    return Dialog(requireActivity()).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        setCanceledOnTouchOutside(false)
        setCancelable(cancelable)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}

fun Fragment.initLoadingProgress(): View =
    LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_loading, getRootView(), false)

fun Fragment.initMessageDialogView(): View =
    LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_message, getRootView(), false)

fun Fragment.getRootView(): ViewGroup {
    return requireActivity().findViewById(android.R.id.content)
}

fun Fragment.showDialog(dialog: Dialog?) {
    if (dialog != null && !dialog.isShowing) {
        dialog.show()
    }
}

fun Fragment.dismissDialog(dialog: Dialog?) {
    if (dialog != null && dialog.isShowing) {
        dialog.dismiss()
    }
}