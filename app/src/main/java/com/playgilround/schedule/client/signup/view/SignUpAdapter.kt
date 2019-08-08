/*
package com.playgilround.schedule.client.signup.view

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.nispok.snackbar.Snackbar
import com.nispok.snackbar.SnackbarManager
import com.nispok.snackbar.enums.SnackbarType
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.data.User
import com.playgilround.schedule.client.signup.model.UserDataModel
import com.playgilround.schedule.client.signup.view.SignUpAdapter.Companion.SIGN_UP_MAX
import kotlinx.android.synthetic.main.item_sign_up_name.view.*

class SignUpAdapter constructor(private val mContext: Context) : RecyclerView.Adapter<SignUpAdapter.RootViewHolder>, UserDataModel {
//
    private var currentPosition = 0

    companion object {
        const val TYPE_NAME = 0
        const val TYPE_EMAIL = 1
        const val TYPE_PASSWORD = 2
        const val TYPE_REPEAT_PASSWORD = 3
        const val TYPE_NICK_NAME = 4
        const val TYPE_BIRTH = 5
        const val SIGN_UP_MAX = 6
    }

    lateinit var mOnSignUpAdapterListener: OnSignUpAdapterListener

    private lateinit var mNameViewHolder: NameViewHolder
    private lateinit var mEmailViewHolder: EmailViewHolder
    private lateinit var mPasswordViewHolder: PasswordViewHolder
    private lateinit var mRepeatPasswordViewHolder: RepeatPasswordViewHolder
    private lateinit var mNickNameViewHolder: NickNameViewHolder
    private lateinit var mBirthViewHolder: BirthViewHolder

    fun setOnSignUpNextFieldListener(onSignUpAdapterListener: OnSignUpAdapterListener) {
        this.mOnSignUpAdapterListener = onSignUpAdapterListener
    }
    @NonNull
    private fun create(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): RootViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)

        return RootViewHolder(binding, mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {
        return create(LayoutInflater.from(parent.context), parent, viewType)
    }

    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return SIGN_UP_MAX
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) {
            R.layout.item_sign_up_name

        } else {


        }
//        return if (position
    }

    //    abstract class RootViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract class RootViewHolder constructor(private val binding: ViewDataBinding, private val mContext: Context) : RecyclerView.ViewHolder(binding.root) {

        var viewPosition: Int = 0
        lateinit var content: String


        abstract fun checkEditText(content: String): Boolean

//        val mOnEditorActionListener = binding.root.edit_sign_up.setOnEditorActionListener { v, actionId, event ->
        val mOnEditorActionListener = binding.root.edit_sign_up.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE && mOnSignUpAdapterListener != null) {
                mOnSignUpAdapterListener.onNextField(position)
                true
            }
            false
        }


        fun bindView(position: Int) {
            viewPosition = position

            binding.run {
                root.edit_sign_up.addTextChangedListener(object : TextWatcher() {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val text = root.edit_sign_up.text.toString().trim()

                        if (checkEditText(text)) {
                            content = text
                            mOnSignUpAdapterListener.ableNextButton()
                            dismissSnackBar()
                        } else {
                            mOnSignUpAdapterListener.disableNextButton()
                            dismissSnackBar()
                        }
                    }

                    override fun afterTextChanged(s: Editable) {

                    }
                })
            }
        }

        fun setFocus() {
            binding.root.edit_sign_up.requestFocus()
        }

        fun hideKeyboard() {
            val imm = binding.root.edit_sign_up.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.edit_sign_up.windowToken, 0)
        }
    }

    class NameViewHolder(itemView: View) : RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return content.isNotEmpty()
        }
    }

    class EmailViewHolder(itemView: View) : RootViewHolder(b) {
        override fun checkEditText(content: String): Boolean {
            return User.checkEmail(content)
        }
    }

    class PasswordViewHolder(itemView: View) : RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return User.checkPassWord(content)
        }
    }

    class RepeatPasswordViewHolder(itemView: View) : RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return true
        }
    }

    class NickNameViewHolder(itemView: View) : RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            //추후에 닉네임 검사 api필요
            return true
        }
    }

    class BirthViewHolder(itemView: View): RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return content != null
        }
    }

    fun showSnackBar() {
        if (currentPosition < SIGN_UP_MAX - 1) {
            val errors = mContext.resources.getStringArray(R.array.error_sign_up_field_check)

            SnackbarManager.show(Snackbar.with(mContext)
                    .type(SnackbarType.MULTI_LINE)
                    .actionLabel(mContext.getString(R.string.error_text_close))
                    .actionColorResource(R.color.error_snack_bar)
                    .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    .text(errors[currentPosition]))
        }
    }

    fun dismissSnackBar() {
        SnackbarManager.dismiss()
    }
}
*/
