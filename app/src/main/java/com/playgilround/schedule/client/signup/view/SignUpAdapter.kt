package com.playgilround.schedule.client.signup.view

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nispok.snackbar.Snackbar
import com.nispok.snackbar.SnackbarManager
import com.nispok.snackbar.enums.SnackbarType
import com.playgilround.schedule.client.R
import com.playgilround.schedule.client.data.User
import com.playgilround.schedule.client.signup.model.UserDataModel
import java.lang.IllegalArgumentException

class SignUpAdapter(private val context: Context): RecyclerView.Adapter<SignUpAdapter.RootViewHolder>(), UserDataModel {
    var position = 0

    private lateinit var mOnSignUpAdapterListener: OnSignUpAdapterListener
    abstract inner class RootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mEditSignUp: EditText = itemView.findViewById(R.id.edit_sign_up)
        internal lateinit var textContent: String
        internal var viewPosition: Int = 0

        private val mOnEditorActionListener = object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE && mOnSignUpAdapterListener != null) {
                    mOnSignUpAdapterListener.onNextField(viewPosition)
                    return true
                }
                return false
            }
        }

        abstract fun checkEditText(content: String): Boolean

        open fun bind(position: Int) {
            viewPosition = position
            mEditSignUp.setOnEditorActionListener(mOnEditorActionListener)
            mEditSignUp.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val text = mEditSignUp.text.toString().trim()
                    if (checkEditText(text)) {
                        textContent = text
                        mOnSignUpAdapterListener.ableNextButton()
                        dismissSnackBar()
                    } else {
                        mOnSignUpAdapterListener.disableNextButton()
                        dismissSnackBar()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }

        fun getContent(): String {
            return textContent
        }

        fun setFocus() {
            mEditSignUp.requestFocus()
        }

        fun hideKeyboard() {
            val imm = mEditSignUp.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mEditSignUp.windowToken, 0)
        }
    }

    companion object {
        const val TYPE_NAME = 0
        const val TYPE_EMAIL = 1
        const val TYPE_PASSWORD = 2
        const val TYPE_REPEAT_PASSWORD = 3
        const val TYPE_NICK_NAME = 4
        const val TYPE_BIRTH = 5
        const val SIGN_UP_MAX = 6
    }

    private lateinit var mNameViewHolder: NameViewHolder
    private lateinit var mEmailViewHolder: EmailViewHolder
    private lateinit var mPasswordViewHolder: PasswordViewHolder
    private lateinit var mRepeatPasswordViewHolder: RepeatPasswordViewHolder
    private lateinit var mNickNameViewHolder: NickNameViewHolder
    private lateinit var mBirthViewHolder: BirthViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {
        return when (viewType) {
            TYPE_NAME -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_sign_up_name, parent, false)
                mNameViewHolder = NameViewHolder(view)
                return mNameViewHolder
            }
            TYPE_EMAIL -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_sign_up_email, parent, false)
                mEmailViewHolder = EmailViewHolder(view)
                return mEmailViewHolder
            }
            TYPE_PASSWORD -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_sign_up_password, parent, false)
                mPasswordViewHolder = PasswordViewHolder(view)
                return mPasswordViewHolder
            }
            TYPE_REPEAT_PASSWORD -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_sign_up_password_check, parent, false)
                mRepeatPasswordViewHolder = RepeatPasswordViewHolder(view)
                return mRepeatPasswordViewHolder
            }
            TYPE_NICK_NAME -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_sign_up_nickname, parent, false)
                mNickNameViewHolder = NickNameViewHolder(view)
                return mNickNameViewHolder
            }
            TYPE_BIRTH -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_sign_up_birth, parent, false)
                mBirthViewHolder = BirthViewHolder(view)
                return mBirthViewHolder
            }
            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }



    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class NameViewHolder(itemView: View): RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return content.isNotEmpty()
        }
    }

    inner class EmailViewHolder(itemView: View): RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return User.checkEmail(content)
        }

    }

    inner class PasswordViewHolder(itemView: View): RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return User.checkPassWord(content)
        }
    }

    inner class RepeatPasswordViewHolder(itemView: View): RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return mPasswordViewHolder.getContent() == content
        }
    }

    inner class NickNameViewHolder(itemView: View): RootViewHolder(itemView) {
        override fun checkEditText(content: String): Boolean {
            return true
        }
    }

    inner class BirthViewHolder(itemView: View): RootViewHolder(itemView) {

        private val mBirth: DatePicker = itemView.findViewById(R.id.datePicker_birth)

        override fun checkEditText(content: String): Boolean {
            return content.isNotEmpty()
        }

        private fun applyStyle(dataPicker: DatePicker) {
            val system = Resources.getSystem()

            val yearID = system.getIdentifier("year", "id", "android")
            val monthID = system.getIdentifier("month", "id", "android")
            val dayID = system.getIdentifier("day", "id", "android")

            val yearNumberPicker = dataPicker.findViewById(yearID) as NumberPicker
            val monthNumberPicker = dataPicker.findViewById(monthID) as NumberPicker
            val dayNumberPicker = dataPicker.findViewById(dayID) as NumberPicker

            setDeviderColor(yearNumberPicker)
            setDeviderColor(monthNumberPicker)
            setDeviderColor(dayNumberPicker)
        }

        private fun setDeviderColor(np: NumberPicker) {

            val count = np.childCount
            for (i in 0..count) {
                val textView = np.getChildAt(i)
                try {
                    val dividerField = np.javaClass.getDeclaredField("mSelectionDivider")
                    val textField = np.javaClass.getDeclaredField("mSelectorWhellPaint")

                    dividerField.isAccessible = true
                    textField.isAccessible = true

                    val colorDrawable = ColorDrawable(context.resources.getColor(R.color.light_indigo))
                    dividerField.set(np, colorDrawable)

                    (textField.get(np) as Paint).color = context.resources.getColor(R.color.light_indigo)
                    (textView as EditText).setTextColor(context.resources.getColor(R.color.light_indigo))

                    np.invalidate()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun bind(position: Int) {
            viewPosition = position
            textContent = "${mBirth.year}-${mBirth.month}-${mBirth.dayOfMonth}"
            mBirth.init(mBirth.year, mBirth.month, mBirth.dayOfMonth) {
                view, year, monthOfYear, dayOfMonth -> setBirth(year, monthOfYear + 1, dayOfMonth) }

            applyStyle(mBirth)
        }

        private fun setBirth(year: Int, month: Int, day: Int) {
            textContent = "$year-$month-$day"
        }
    }

    override fun getNameField(): String {
        return mNameViewHolder.getContent()
    }

    override fun getEmailField(): String {
        return mEmailViewHolder.getContent()
    }

    override fun getPasswordField(): String {
        return mPasswordViewHolder.getContent()
    }

    override fun getRepeatPasswordField(): String {
        return mRepeatPasswordViewHolder.getContent()
    }

    override fun getNicknameField(): String {
        return mNickNameViewHolder.getContent()
    }

    override fun getBirthField(): String {
        return mBirthViewHolder.getContent()
    }

    fun setFocus() {
        when (this.position) {
            TYPE_NAME -> mNameViewHolder.setFocus()
            TYPE_EMAIL -> mEmailViewHolder.setFocus()
            TYPE_PASSWORD -> mPasswordViewHolder.setFocus()
            TYPE_REPEAT_PASSWORD -> mRepeatPasswordViewHolder.setFocus()
            TYPE_NICK_NAME -> mNickNameViewHolder.setFocus()
            TYPE_BIRTH -> {
                mBirthViewHolder.setFocus()
                mBirthViewHolder.hideKeyboard()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return SIGN_UP_MAX
    }

    fun setOnSignUpNextFieldListener(onSignUpAdapterListener: OnSignUpAdapterListener) {
        mOnSignUpAdapterListener = onSignUpAdapterListener
    }

    fun showSnackBar() {
        if (this.position < SIGN_UP_MAX -1) {
            val errors = context.resources.getStringArray(R.array.error_sign_up_field_check)

            SnackbarManager.show(Snackbar.with(context)
                    .type(SnackbarType.MULTI_LINE)
                    .actionLabel(context.getString(R.string.error_text_close))
                    .actionColorResource(R.color.error_snack_bar)
                    .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                    .text(errors[this.position]))
        }
    }

    fun dismissSnackBar() {
        SnackbarManager.dismiss()
    }
}
