/*
 * Copyright 2014 Benjamin Linskey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.benlinskey.greekreference.data.syntax;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * An {@link SQLiteAssetHelper} for the syntax database.
 */
public class SyntaxHelper extends SQLiteAssetHelper {
    private static final int DB_VERSION = 1;

    /**
     * Class constructor.
     * @param context   the <code>Context</code> to use
     */
    public SyntaxHelper(Context context) {
        super(context, SyntaxContract.DB_NAME, null, DB_VERSION);
        setForcedUpgrade(DB_VERSION); // Copy entire database on upgrade.
    }
}
