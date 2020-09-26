#ifndef SK2_USER_H
#define SK2_USER_H

#include <string>
#include <vector>

using namespace std;

class User {

    string login;
    string password;
    int mysocket1;
    int mysocket2;
    bool loged;
    bool istolking;
    bool iscalling;
    int wsize = 0;
    int ssize = 0;
    int x = 0;
    int y = 0;
    int *end;
    User *withWho;
    vector<User *> friends;
public:

    User(string login, string password, int s1 = -1, int s2 = -1, bool loged = false, bool istolking = false) {
        this->password = password;
        this->login = login;
        this->loged = loged;
        this->mysocket1 = s1;
        this->mysocket2 = s2;
        this->istolking = istolking;
    }

    int *getEnd() const {
        return end;
    }

    void setEnd(int *end) {
        User::end = end;
    }

    User *getWithWho() const {
        return withWho;
    }

    void setWithWho(User *withWho) {
        User::withWho = withWho;
    }

    bool isIstolking() const {
        return istolking;
    }

    bool isIscalling() const {
        return iscalling;
    }

    void setIscalling(bool iscalling) {
        User::iscalling = iscalling;
    }

    int getWsize() const {
        return wsize;
    }

    void setWsize(int wsize) {
        User::wsize = wsize;
    }

    int getSsize() const {
        return ssize;
    }

    void setSsize(int ssize) {
        User::ssize = ssize;
    }

    int getX() const {
        return x;
    }

    void setX(int x) {
        User::x = x;
    }

    int getY() const {
        return y;
    }

    void setY(int y) {
        User::y = y;
    }


    void setIstolking(bool istolking) {
        User::istolking = istolking;
    }

    const string &getLogin() const {
        return login;
    }

    void setLogin(string log) {
        User::login = log;
    }

    const string &getPassword() const {
        return password;
    }

    void setPassword(string pass) {
        User::password = pass;
    }

    int getMysocket1() const {
        return mysocket1;
    }

    void setMysocket1(int mysock1) {
        User::mysocket1 = mysock1;
    }

    int getMysocket2() const {
        return mysocket2;
    }

    void setMysocket2(int mysock2) {
        User::mysocket2 = mysock2;
    }

    bool isLoged() const {
        return loged;
    }

    void setLoged(bool isloged) {
        User::loged = isloged;
    }

    vector<User *> getFriends() {
        return friends;
    }

    void addNewFriend(User *new_friend) {
        friends.push_back(new_friend);
    }

    void removeFriend(int index) {
        friends.erase(friends.begin() + index);
    }

};


#endif //SK2_USER_H
