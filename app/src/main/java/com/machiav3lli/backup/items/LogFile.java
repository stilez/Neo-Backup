/*
 * OAndBackupX: open-source apps backup and restore app.
 * Copyright (C) 2020  Antonios Hazim
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.machiav3lli.backup.items;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.machiav3lli.backup.Constants;
import com.machiav3lli.backup.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile implements Parcelable {
    public static final Parcelable.Creator<LogFile> CREATOR = new Parcelable.Creator<LogFile>() {
        public LogFile createFromParcel(Parcel in) {
            return new LogFile(in);
        }

        public LogFile[] newArray(int size) {
            return new LogFile[size];
        }
    };
    private static final String TAG = Constants.classTag(".LogFile");
    String label, packageName, versionName, sourceDir, dataDir, deviceProtectedDataDir;
    String[] splitSourceDirs;
    int versionCode, backupMode;
    long lastBackupMillis;
    boolean encrypted, system;

    public LogFile(File backupSubDir, String packageName) {
        LogUtils frw = new LogUtils(backupSubDir.getAbsolutePath(), packageName + ".log");
        String json = frw.read();
        try {
            JSONObject jsonObject = new JSONObject(json);
            this.label = jsonObject.getString("label");
            this.packageName = jsonObject.getString("packageName");
            this.versionName = jsonObject.getString("versionName");
            this.sourceDir = jsonObject.getString("sourceDir");
            this.splitSourceDirs = jsonObject.has("splitSourceDirs") ? LogFile.toStringArray(jsonObject.getJSONArray("splitSourceDirs")) : null;
            this.dataDir = jsonObject.getString("dataDir");
            this.deviceProtectedDataDir = jsonObject.has("deviceProtectedDataDir") ? jsonObject.getString("deviceProtectedDataDir") : null;
            this.lastBackupMillis = jsonObject.getLong("lastBackupMillis");
            this.versionCode = jsonObject.getInt("versionCode");
            this.encrypted = jsonObject.optBoolean("isEncrypted");
            this.system = jsonObject.optBoolean("isSystem");
            this.backupMode = jsonObject.optInt("backupMode", AppInfo.MODE_UNSET);
        } catch (JSONException e) {
            Log.e(TAG, packageName + ": error while reading logfile: " + e.toString());
            this.label = this.packageName = this.versionName = this.sourceDir = this.dataDir = "";
            this.lastBackupMillis = this.versionCode = 0;
            this.encrypted = this.system = false;
            this.backupMode = AppInfo.MODE_UNSET;
        }
    }

    private LogFile(Parcel in) {
        // data is read in the order it was written
        label = in.readString();
        packageName = in.readString();
        versionName = in.readString();
        sourceDir = in.readString();
        splitSourceDirs = in.createStringArray();
        dataDir = in.readString();
        versionCode = in.readInt();
        backupMode = in.readInt();
        lastBackupMillis = in.readLong();
        boolean[] bools = new boolean[2];
        in.readBooleanArray(bools);
        encrypted = bools[0];
        system = bools[1];
    }

    public static void writeLogFile(File backupSubDir, AppInfo appInfo, int backupMode, boolean encrypted) throws IOException, JSONException {
        try {
            // path to apk should only be logged if it is backed up
            String sourceDir = "";
            String[] splitSourceDirs = null;
            if (backupMode == AppInfo.MODE_APK || backupMode == AppInfo.MODE_BOTH) {
                sourceDir = appInfo.getSourceDir();
                splitSourceDirs = appInfo.getSplitSourceDirs();
            } else if (appInfo.getLogInfo() != null) {
                sourceDir = appInfo.getLogInfo().getSourceDir();
                splitSourceDirs = appInfo.getLogInfo().getSplitSourceDirs();
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("label", appInfo.getLabel());
            jsonObject.put("versionName", appInfo.getVersionName());
            jsonObject.put("versionCode", appInfo.getVersionCode());
            jsonObject.put("packageName", appInfo.getPackageName());
            jsonObject.put("sourceDir", sourceDir);
            // Value won't be written to the json string if it's java's null. Fine.
            jsonObject.put("splitSourceDirs", LogFile.toJsonArray(splitSourceDirs));
            jsonObject.put("dataDir", appInfo.getDataDir());
            jsonObject.put("deviceProtectedDataDir", appInfo.getDeviceProtectedDataDir());
            jsonObject.put("lastBackupMillis", System.currentTimeMillis());
            jsonObject.put("isEncrypted", encrypted);
            jsonObject.put("isSystem", appInfo.isSystem());
            jsonObject.put("backupMode", appInfo.getBackupMode());
            String json = jsonObject.toString(4);
            File outFile = new File(backupSubDir, appInfo.getPackageName() + ".log");
            outFile.createNewFile();
            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(outFile.getAbsoluteFile()))) {
                bw.write(json + "\n");
            }
        } catch (JSONException | IOException e) {
            Log.e(TAG, "LogFile.writeLogFile: " + e.toString());
            throw e;
        }
    }

    public static JSONArray toJsonArray(Object[] array) {
        if (array == null) {
            return null;
        }
        JSONArray result = new JSONArray();
        for (Object entry : array) {
            result.put(entry);
        }
        return result;
    }

    public static String[] toStringArray(JSONArray array) {
        String[] result = new String[array.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = array.optString(i);
        }
        return result;
    }

    public String getLabel() {
        return label;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public String[] getSplitSourceDirs() {
        return splitSourceDirs;
    }

    public String[] getSplitApks() {
        if (splitSourceDirs != null) {
            String[] result = new String[splitSourceDirs.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = splitSourceDirs[i].substring(sourceDir.lastIndexOf("/") + 1);
            }
            return result;
        }
        return new String[0];
    }

    public String getApk() {
        if (sourceDir != null && sourceDir.length() > 0)
            return sourceDir.substring(sourceDir.lastIndexOf("/") + 1);
        return null;
    }

    public String getDataDir() {
        return dataDir;
    }

    public String getDeviceProtectedDataDir() {
        return deviceProtectedDataDir;
    }

    public long getLastBackupMillis() {
        return lastBackupMillis;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public boolean isSystem() {
        return system;
    }

    public int getBackupMode() {
        return backupMode;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(label);
        out.writeString(packageName);
        out.writeString(versionName);
        out.writeString(sourceDir);
        out.writeStringArray(splitSourceDirs);
        out.writeString(dataDir);
        out.writeInt(versionCode);
        out.writeInt(backupMode);
        out.writeLong(lastBackupMillis);
        out.writeBooleanArray(new boolean[]{encrypted, system});
    }
}
