package com.samcho.user_authentication.domain.core.util

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class IdGenerator(
    private val entityName : String,
) : IdentifierGenerator {
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable? {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val prefix = "${entityName.uppercase()}_${LocalDate.now().format(formatter)}_"

        val connection = session?.connection()

        try {
            val statement = connection?.createStatement()
            val rs : ResultSet? = statement?.executeQuery("select next_val(\"${entityName.uppercase()}_SEQ\")")
            if(rs != null && rs.next()) {
                return  prefix + "${rs.getInt(1)}".padStart(8, '0')
            }

        } catch (e : SQLException) {
            e.printStackTrace()
        }
        return null
    }

    fun generateId(next: Int): String  {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val prefix = "${entityName.uppercase()}_${LocalDate.now().format(formatter)}_"
        return prefix + "$next".padStart(8, '0')
    }
}