package com.example.tictactoeonline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object GameData {
    private var _gamemodel : MutableLiveData<GameModel> = MutableLiveData()
    var gameModel : LiveData<GameModel> = _gamemodel
    var myID = ""

    fun saveGameModel(model : GameModel){
        _gamemodel.postValue(model)

        if(model.gameId!="-1"){
            Firebase.firestore.collection("games")
                .document(model.gameId)
                .set(model)
        }
    }
    fun fetchGameModel(){
        gameModel.value?.apply {
            if(gameId!="-1"){
                Firebase.firestore.collection("games")
                    .document(gameId)
                    .addSnapshotListener{ value, error ->
                        val model = value?.toObject(GameModel::class.java)
                        _gamemodel.postValue(model)
                    }
            }
        }
    }
}