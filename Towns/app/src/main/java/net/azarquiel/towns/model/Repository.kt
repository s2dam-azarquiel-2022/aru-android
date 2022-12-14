package net.azarquiel.towns.model

import android.app.Application
import androidx.lifecycle.LiveData

class CommunityRepository(application: Application) {
    private val communityDAO = TownsDB.getDB(application).communityDAO()

    fun getAll(): LiveData<List<Community>> = communityDAO.getAll()
}

class ProvinceRepository(application: Application) {
    private val provinceDAO = TownsDB.getDB(application).provinceDAO()
}

class TownRepository(application: Application) {
    private val townDAO = TownsDB.getDB(application).townDAO()

    fun getByCommunityID(communityID: Int): LiveData<List<TownView>> =
        townDAO.getByCommunityID(communityID)
    fun getById(id: Int): LiveData<List<TownView>> = townDAO.getById(id)
    fun toggleFav(id: Int) = townDAO.toggleFav(id)
}
