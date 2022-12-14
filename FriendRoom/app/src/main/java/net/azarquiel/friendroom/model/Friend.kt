package net.azarquiel.friendroom.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "Friends")
data class Friend(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "email")
    var email: String = "",

    @ColumnInfo(name = "groupID")
    @ForeignKey(entity = Group::class, childColumns = ["groupID"], parentColumns = ["id"])
    var groupID: Int = 0,
)

@Dao
interface FriendDAO {
    @Query("SELECT * FROM Friends ORDER BY name ASC")
    fun getAll(): LiveData<List<Friend>>

    @Query("SELECT * FROM Friends WHERE id = :id")
    fun getById(id: Int): LiveData<List<Friend>>

    @Query("SELECT * FROM Friends WHERE groupID = :groupID")
    fun getByGroupId(groupID: Int): LiveData<List<Friend>>

    @Insert
    fun add(friend: Friend)

    @Query("DELETE FROM Friends WHERE id = :id")
    fun remove(id: Int)

    @Update
    fun update(friend: Friend)
}
