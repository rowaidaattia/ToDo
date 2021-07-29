package com.rowaida.todo.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: UserEntity) : Long

    @Query("SELECT EXISTS(SELECT * FROM User WHERE " +
            "(username = :usernameOrEmail OR email = :usernameOrEmail) AND password = :password)")
    fun checkValidCredentials(usernameOrEmail: String, password: String) : Boolean

    @Query("SELECT username FROM User WHERE username = :usernameOrEmail OR email = :usernameOrEmail LIMIT 1")
    fun getUsername(usernameOrEmail: String) : String

    @Query("UPDATE User SET admin = :admin, accountType = 'SUB_ACCOUNT' WHERE username = :subAccount")
    fun addSubAccount(admin: String, subAccount: String)

    @Query("SELECT username FROM User WHERE admin = :username AND admin != username")
    fun getSubAccounts(username: String) : List<String>

    @Query("SELECT accountType FROM User WHERE username = :username LIMIT 1")
    fun getAccountType(username: String) : String

    @Query("SELECT username FROM User WHERE username != :username")
    fun getAccounts(username: String) : List<String>

}