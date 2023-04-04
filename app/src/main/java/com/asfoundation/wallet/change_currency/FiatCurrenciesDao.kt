package com.asfoundation.wallet.change_currency

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FiatCurrenciesDao {
  @Query(
    "SELECT * FROM FiatCurrencyEntity WHERE currency = :currency LIMIT 1"
  )
  fun getFiatCurrency(currency: String): FiatCurrencyEntity

  @Query("SELECT *  FROM FiatCurrencyEntity")
  fun getFiatCurrencies(): List<FiatCurrencyEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveCurrency(fiatCurrency: FiatCurrencyEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(fiatCurrencyList: List<FiatCurrencyEntity>)

  @Query("DELETE FROM FiatCurrencyEntity")
  fun removeAll()

  @Transaction
  fun replaceAllBy(currencies: List<FiatCurrencyEntity>) {
    removeAll()
    saveAll(currencies)
  }
}