package com.semashko.maximaps.data.route

import com.google.firebase.database.FirebaseDatabase

class ChatReferences {

    fun chatReferences() = FirebaseDatabase.getInstance().getReference("chat")
}