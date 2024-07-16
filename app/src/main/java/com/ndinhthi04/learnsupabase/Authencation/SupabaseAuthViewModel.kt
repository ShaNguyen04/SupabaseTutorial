package com.ndinhthi04.learnsupabase.Authencation

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndinhthi04.learnsupabase.Authencation.data.model.UseState
import com.ndinhthi04.learnsupabase.Authencation.data.network.SupabaseClient.client
import com.ndinhthi04.learnsupabase.Authencation.utils.SharedReferenceHelper
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

class SupabaseAuthViewModel : ViewModel() {
    private val _useState = mutableStateOf<UseState>(UseState.Loading)

    val userState: State<UseState> = _useState

    //Chức năng Đăng ký
    fun SignUp(
        context: Context,
        userEmail: String,
        userPassword: String,
    ) {
        viewModelScope.launch {
            //Loading
            _useState.value = UseState.Loading
            try {
                //Kiem tra email va mat khau
                client.gotrue.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                //luu token dang nhap
                saveToken(context)
                _useState.value = UseState.Success("Register User Successfull")
            } catch (e: Exception) {
                _useState.value = UseState.Error("Error: ${e.message}")
            }
        }
    }


    //Chức năng Đăng nhập
    fun Login(
        context: Context,
        userEmail: String,
        userPassword: String,
    ) {
        viewModelScope.launch {
            //Loading
            _useState.value = UseState.Loading
            try {
                //Kiem tra email va mat khau
                client.gotrue.loginWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                //luu token dang nhap
                saveToken(context)
                //thong bao dang nhap thanh cong
                _useState.value = UseState.Success("Login User Successfull")
            } catch (e: Exception) {
                _useState.value = UseState.Error("Error: ${e.message}")
            }
        }
    }

    //Chức năng Đăng xuất
    fun logOut(context: Context) {
        val sharedPref = SharedReferenceHelper(context)
        viewModelScope.launch {
            //Loading
            _useState.value = UseState.Loading
            try {
                //Dang xuat
                client.gotrue.logout()
                //Xoa token dang nhap
                sharedPref.clearPreference()
                //thong bao dang xuat thanh cong
                _useState.value = UseState.Success("Log out User Successfull")
            } catch (e: Exception) {
                _useState.value = UseState.Error("Error: ${e.message}")
            }
        }
    }

    //Ham luu token cua tai khoan dang nhap
    private fun saveToken(context: Context) {
        val accessToken = client.gotrue.currentAccessTokenOrNull()
        val sharedPref = SharedReferenceHelper(context)
        sharedPref.saveStringData("accessToken", accessToken)
    }

    //Ham lay token cua tai khoan dang nhap
    private fun getToken(context: Context): String? {
        val sharedPref = SharedReferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    //Kiem tra tai khoan dang nhap
    fun isUserLogin(context: Context) {
        viewModelScope.launch {
            try {
                //Lay Token cua tai khoan
                val token = getToken(context)
                //Kiem tra token co null hay khong
                if (token.isNullOrEmpty()){
                    _useState.value = UseState.Error("User not login")
                }else{
                    //Lay thong tin tai khoan
                    client.gotrue.retrieveUser(token)
                    //Kiem tra tai khoan da login hay chua
                    client.gotrue.refreshCurrentSession()
                    //Luu lai token
                    saveToken(context)
                    _useState.value = UseState.Success("User already login!")
                }
            } catch (e: Exception) {
                _useState.value = UseState.Error("Error: ${e.message}")
            }
        }
    }

}