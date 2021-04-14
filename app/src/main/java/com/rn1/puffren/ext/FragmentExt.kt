package com.rn1.puffren.ext

import androidx.fragment.app.Fragment
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.data.Product
import com.rn1.puffren.data.User
import com.rn1.puffren.factory.ProdItemViewModelFactory
import com.rn1.puffren.factory.ProductViewModelFactory
import com.rn1.puffren.factory.UserViewModelFactory
import com.rn1.puffren.factory.ViewModelFactory
import com.rn1.puffren.ui.product.ProdTypeFilter


fun Fragment.getVmFactory(): ViewModelFactory{
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(user: User): UserViewModelFactory{
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return UserViewModelFactory(repository, user)
}

fun Fragment.getVmFactory(prodTypeFilter: ProdTypeFilter): ProdItemViewModelFactory{
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return ProdItemViewModelFactory(repository, prodTypeFilter)
}

fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
    val repository = (requireContext().applicationContext as PuffRenApplication).puffRenRepository
    return ProductViewModelFactory(repository, product)
}