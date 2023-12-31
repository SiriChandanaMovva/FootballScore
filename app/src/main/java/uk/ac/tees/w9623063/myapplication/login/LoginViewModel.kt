package uk.ac.tees.w9623063.myapplication.login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uk.ac.tees.w9623063.myapplication.repository.AuthRepository
import kotlinx.coroutines.launch
import kotlin.IllegalArgumentException

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
):ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean get() = repository.hasUser()
    var loginUiState by mutableStateOf(LoginUiState())
        private set
    fun onUserNameChange(userName: String){
        loginUiState = loginUiState.copy(userName = userName)
    }
    fun onPasswordChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }
    fun onUsernameChangeSignUp(userNameSignUp: String){
        loginUiState = loginUiState.copy(userNameSignUp = userNameSignUp)
    }
    fun onPasswordChangeSignUp(passwordSignUp: String){
        loginUiState = loginUiState.copy(passwordSignUp = passwordSignUp)
    }
    fun onConfirmPasswordChangedSignUp(confirmPasswordSignUp: String){
        loginUiState = loginUiState.copy(confirmPasswordSignUp = confirmPasswordSignUp)
    }
    private fun validateLoginForm() =
        loginUiState.userName.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignUpForm() =
        loginUiState.userNameSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if(!validateSignUpForm()){
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)

            if(loginUiState.passwordSignUp !=
                loginUiState.confirmPasswordSignUp){
                throw IllegalArgumentException("Password do not match")
            }
            loginUiState = loginUiState.copy(signUpError = null)

            repository.createUser(
                loginUiState.userNameSignUp,
                loginUiState.passwordSignUp
            ){ isSuccessful ->
                if (isSuccessful){
                    Toast.makeText(context,"Created account successfully",Toast.LENGTH_LONG).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                }else{
                    Toast.makeText(context,"Failed to create account", Toast.LENGTH_LONG).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }
            }

        }catch (e:Exception){
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
            Log.e(TAG, e.localizedMessage)
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if(!validateLoginForm()){
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.userName,
                loginUiState.password,
            ){ isSuccessful ->
                if(isSuccessful){
                    Toast.makeText(context,"success Login",Toast.LENGTH_LONG).show()
                    loginUiState = loginUiState.copy(isSuccessLogin =  true)
                }else{
                    Toast.makeText(context,"Failed Login",Toast.LENGTH_LONG).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)

                }
            }

        }catch (e:Exception){
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
            Log.e(TAG, e.localizedMessage)
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }
}

data class LoginUiState(
    val userName:String = "",
    val password:String = "",
    val userNameSignUp:String = "",
    val passwordSignUp:String = "",
    val confirmPasswordSignUp:String = "",
    val isLoading:Boolean= false,
    val isSuccessLogin:Boolean = false,
    val isSuccessSignUp:Boolean = false,
    val signUpError:String? = null,
    val loginError:String? = null
)