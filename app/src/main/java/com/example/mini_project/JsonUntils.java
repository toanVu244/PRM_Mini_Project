package com.example.mini_project;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUntils {

public static List<Account> readUserListFromFile(Context context, String fileName) {
    List<Account> userList = new ArrayList<>();
    try {
        FileInputStream fis = context.openFileInput(fileName);
        InputStreamReader isr = new InputStreamReader(fis);
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(isr);
        Account[] accounts = gson.fromJson(bufferedReader, Account[].class);
        userList = new ArrayList<>(Arrays.asList(accounts));
        bufferedReader.close();
        File file = context.getFileStreamPath(fileName);
        Log.d("File Path -read", "Path to JSON file: " + file.getAbsolutePath());
    } catch (FileNotFoundException e) {
        // File not found, return empty list
        return userList;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return userList;
}
public static void writeUserListToFile(Context context, List<Account> userList, String fileName) {
    try {
        File file = context.getFileStreamPath(fileName);
        Log.d("File Path -write", "Path to JSON file: " + file.getAbsolutePath());
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        Gson gson = new Gson();
        String json = gson.toJson(userList);
        Log.d("JSON To Write", "Chuỗi JSON trước khi ghi: " + json);
        osw.write(json);
        osw.close();
        Log.d("File Size", "Kích thước file sau khi ghi: " + file.length() + " bytes");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public static boolean login(Context ct, String usernam, String pass) {
        List<Account> list = readUserListFromFile(ct, "Account.json");
        if (list != null) {
            Log.d("Login Check", "List size: " + list.size());
            for (Account a : list) {
                if (a.getUsername() != null && a.getPassword() != null &&
                        a.getUsername().equals(usernam) && a.getPassword().equals(pass)) {
                    Log.d("Login Check", "Login success for user: " + usernam);
                    return true;
                }
            }
        } else {
            Log.d("Login Check", "Account list is null or empty");
        }
        Log.d("Login Check", "Login failed for user: " + usernam);
        return false;
    }
    public static boolean register(Context ct, String username, String password) {
        List<Account> userList = readUserListFromFile(ct, "Account.json");

        if (userList == null) {
            userList = new ArrayList<>();
        }

        for (Account account : userList) {
            if (account.getUsername().equals(username)) {
                Log.d("Register Check", "Username already exists: " + username);
                return false; // Tên người dùng đã tồn tại
            }
        }
        List<History> histories = new ArrayList<>();
        Account newAccount = new Account(histories, 0, password, username, "USER" + (userList.size() + 1));
        userList.add(newAccount);

        writeUserListToFile(ct, userList, "Account.json");
        Log.d("Register Check", "Registration successful for user: " + username);

        return true;
    }

    public static List<History> getHistories(Context ct, String username) {
        List<Account> userList = readUserListFromFile(ct, "Account.json");

        if (userList == null || userList.isEmpty()) {
            Log.d("GetHistories", "User list is empty or not found.");
            return null;
        }

        for (Account account : userList) {
            if (account.getUsername().equals(username)) {
                Log.d("GetHistories", "Found user: " + username);
                return account.getHistories();
            }
        }
        Log.d("GetHistories", "No user found with username: " + username);
        return null;
    }

    public static boolean addHistoryToAccount(Context ct, String username, History newHistory) {
        // Đọc danh sách tài khoản từ file JSON
        List<Account> userList = readUserListFromFile(ct, "Account.json");

        if (userList != null) {
            // Duyệt qua danh sách để tìm tài khoản cần thêm lịch sử
            for (Account account : userList) {
                if (account.getUsername().equals(username)) {
                    // Thêm History mới vào danh sách histories của tài khoản
                    account.getHistories().add(newHistory);

                    // Ghi lại danh sách tài khoản vào file JSON để lưu thay đổi
                    writeUserListToFile(ct, userList, "Account.json");
                    return true; // Thêm thành công
                }
            }
        }

        return false; // Không tìm thấy tài khoản hoặc danh sách trống
    }
    public static Account getAccountInfo(Context context, String username) {
        List<Account> userList = JsonUntils.readUserListFromFile(context, "Account.json");
        if (userList != null) {
            for (Account account : userList) {
                if (account.getUsername().equals(username)) {
                    return account;
                }
            }
        }
        return null; // Nếu không tìm thấy tài khoản
    }
    public static boolean updateMoneyForAccount(Context context, String username, int newMoney) {
        List<Account> userList = JsonUntils.readUserListFromFile(context, "Account.json");
        if (userList != null) {
            for (Account account : userList) {
                if (account.getUsername().equals(username)) {
                    account.setMoney(newMoney);
                    JsonUntils.writeUserListToFile(context, userList, "Account.json");
                    return true;
                }
            }
        }
        return false;
    }



}
