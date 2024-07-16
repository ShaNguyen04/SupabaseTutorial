package com.ndinhthi04.learnsupabase.Authencation.data.model

// UseState User
sealed class UseState {
    // Đối tượng đại diện cho trạng thái Loading
    object Loading: UseState()

    // Lớp dữ liệu đại diện cho trạng thái thành công với thông điệp kèm theo
    data class Success(val message: String): UseState()

    // Lớp dữ liệu đại diện cho trạng thái lỗi với thông điệp kèm theo
    data class Error(val message: String): UseState()
}
