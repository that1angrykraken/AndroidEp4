package seamonster.kraken.androidep4

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class TaskProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = "seamonster.kraken.androidep4.TaskProvider"
        private const val PATH = "tasks"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

        const val table_name = "tasks"
        const val uriCode = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, PATH, uriCode)
            uriMatcher.addURI(AUTHORITY, "$PATH/*", uriCode)
        }
    }

    private lateinit var database: SQLiteDatabase

    override fun onCreate(): Boolean {
        database = DbHelper(context!!).writableDatabase
        return true
    }

    override fun getType(uri: Uri): String {
        when(uriMatcher.match(uri)) {
            uriCode -> return "vnd.android.cursor.dir/tasks"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            uriCode -> {
                val id = database.insert(table_name, null, values)
                context?.contentResolver?.notifyChange(uri, null)
                return Uri.parse("$CONTENT_URI/$id")
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = table_name

        when (uriMatcher.match(uri)) {
            uriCode -> { }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder)
        cursor?.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }
}