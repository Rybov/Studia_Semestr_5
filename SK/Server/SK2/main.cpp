#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <pthread.h>
#include <iostream>
#include <string>
#include <vector>
#include <sstream>
#include <thread>
#include <csignal>
#include "User.h"

using namespace std;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
struct t_data {
    User *TUser;
    int socket;
    vector<User *> *tallUsers;
};
struct t_data2 {
    int u1;
    int u2;
};
struct message {
    int size;
    char type[10];
    char *messagetext;
};
char *lof(User *user) {
    stringstream friends;
    char *msg;
    pthread_mutex_lock(&mutex);

    for (long unsigned int x = 0; x < user->getFriends().size(); x++) {
        friends << user->getFriends()[x]->getLogin() << '\n';
    }
    pthread_mutex_unlock(&mutex);
    msg = new char[friends.str().length()];
    strcpy(msg, friends.str().c_str());
    return msg;
}

message getMessage(int socket) {
    struct message msg;
    char onechar, messgsize[1000];
    int x = 0, y = 0;
    x = read(socket, &onechar, 1);
    if (x == 0) {
        msg.messagetext = new char[2];
        strcpy(msg.type, "");
        strcpy(msg.messagetext, "");
        return msg;
    }
    while (onechar != '\n') {
        messgsize[y] = onechar;
        y += x;
        x = read(socket, &onechar, 1);
    }
    msg.size = atoi(messgsize);
    y = 0;
    x = read(socket, &onechar, 1);
    memset(msg.type, 0, sizeof(msg.type));
    while (onechar != '\n') {
        msg.type[y] = onechar;
        y += x;
        x = read(socket, &onechar, 1);
    }
    msg.messagetext = new char[msg.size];
    y = 0;
    while (y < msg.size) {
        x = read(socket, msg.messagetext + y, msg.size - y);
        y += x;
    }
    cout << "Odebrana wiadomość: " << msg.type << msg.messagetext << endl;
    return msg;
}

void sendMessage(int socket, char *type, char *message) {
    cout << "Wysłana wiadomość: " << type << message << endl;
    char buff[1000];
    memset(buff, 0, sizeof(buff));
    sprintf(buff, "%ld", strlen(message));
    write(socket, buff, strlen(buff));
    write(socket, "\n", 1);
    write(socket, type, strlen(type));
    write(socket, "\n", 1);
    write(socket, message, strlen(message));
    write(socket, "\n", 1);
}

void checkCall(t_data *tdata, message msg) {
    string friendname, wsize, x, y, ssize;
    stringstream msg2;
    stringstream ss(msg.messagetext);
    getline(ss, friendname, '\n');
    getline(ss, wsize, '\n');
    getline(ss, x, '\n');
    getline(ss, y, '\n');
    getline(ss, ssize, '\n');
    pthread_mutex_lock(&mutex);
    for (long unsigned int xx = 0; xx < tdata->tallUsers->size(); xx++) {
        if (tdata->tallUsers->at(xx)->getLogin().compare(friendname) == 0) {
            if (tdata->tallUsers->at(xx)->isLoged()) {
                if (!tdata->tallUsers->at(xx)->isIstolking() && !tdata->tallUsers->at(xx)->isIscalling()) {
                    tdata->TUser->setIscalling(true);
                    tdata->TUser->setX(atoi(x.c_str()));
                    tdata->TUser->setY(stoi(y.c_str()));
                    tdata->TUser->setWsize(stoi(wsize.c_str()));
                    tdata->TUser->setSsize(stoi(ssize.c_str()));
                    msg2 << tdata->TUser->getLogin() << "\n" << wsize << "\n" << x << "\n" << y << "\n" << ssize;
                    msg.messagetext = new char[msg2.str().length()];
                    strcpy(msg.messagetext, msg2.str().c_str());
                    strcpy(msg.type, "C");
                    tdata->tallUsers->at(xx)->setWithWho(tdata->TUser);
                    tdata->TUser->setWithWho(tdata->tallUsers->at(xx));
                    sendMessage(tdata->tallUsers->at(xx)->getMysocket1(), msg.type, msg.messagetext);
                    break;
                } else {
                    strcpy(msg.type, "IT");
                    strcpy(msg.messagetext, "");
                    sendMessage(tdata->socket, msg.type, msg.messagetext);
                    break;
                }
            } else {

                strcpy(msg.messagetext, "");
                strcpy(msg.type, "ILO");
                sendMessage(tdata->socket, msg.type, msg.messagetext);
                break;
            }
        }
    }
    pthread_mutex_unlock(&mutex);
}

void *tb2(void *t_d) {

    struct t_data2 *tData2 = (struct t_data2 *) t_d;
    char wideo1[1000000];
    int x = 1;
    while (1) {
        x = read(tData2->u1, wideo1, 1000000);
        if (x == 0) {
            break;
        }
        send(tData2->u2, wideo1, x, MSG_NOSIGNAL);
    }
    cout << "koniec rozmowy" << endl;
    pthread_exit(NULL);
}

void videoStreamSocket(t_data *tdata, message msg) {
    string name;
    cout << "nowy wideo socket" << endl;
    stringstream ss(msg.messagetext);
    getline(ss, name, '\n');
    pthread_mutex_lock(&mutex);
    for (long unsigned int su = 0; su < tdata->tallUsers->size(); su++) {
        if (tdata->tallUsers->at(su)->getLogin().compare(name) == 0) {
            tdata->tallUsers->at(su)->setMysocket2(tdata->socket);
            break;
        }
    }
    pthread_mutex_unlock(&mutex);
}

void yes(t_data *tData, char *msg) {
    pthread_mutex_lock(&mutex);
    if (tData->TUser->getWithWho()->isLoged()) {
        sleep(1);
        char x[10];
        struct t_data2 *data = new t_data2();
        struct t_data2 *data2 = new t_data2();
        data->u1 = tData->TUser->getMysocket2();
        data->u2 = tData->TUser->getWithWho()->getMysocket2();
        data2->u1 = tData->TUser->getWithWho()->getMysocket2();
        data2->u2 = tData->TUser->getMysocket2();
        strcpy(x, "Y");
        sendMessage(tData->TUser->getWithWho()->getMysocket1(), x, msg);
        pthread_t thread[2];
        int tr = pthread_create(&thread[1], NULL, tb2, (void *) data);
        if (tr < 0) {
            perror("Creating a thread failed");
            exit(-1);
        }
        tr = pthread_create(&thread[2], NULL, tb2, (void *) data2);
        if (tr < 0) {
            perror("Creating a thread failed");
            exit(-1);
        }
        pthread_detach(tr);

    } else {
        tData->TUser->getWithWho()->setWithWho(nullptr);
        tData->TUser->setWithWho(nullptr);
    }
    pthread_mutex_unlock(&mutex);
}

void no(t_data *tData) {
    pthread_mutex_lock(&mutex);
    if (tData->TUser->getWithWho()->isLoged()) {
        struct message msg;
        strcpy(msg.type, "N");
        strcpy(msg.messagetext, "");
        sendMessage(tData->TUser->getWithWho()->getMysocket1(), msg.type, msg.messagetext);
        tData->TUser->getWithWho()->setWithWho(nullptr);
        tData->TUser->setWithWho(nullptr);
    } else {
        tData->TUser->getWithWho()->setWithWho(nullptr);
        tData->TUser->setWithWho(nullptr);
    }
    pthread_mutex_unlock(&mutex);
}

void logged(t_data *tdata) {
    struct message msg;
    pthread_mutex_lock(&mutex);
    tdata->TUser->setLoged(true);
    tdata->TUser->setIscalling(false);
    tdata->TUser->setIstolking(false);
    pthread_mutex_unlock(&mutex);
    strcpy(msg.type, "F");
    sendMessage(tdata->socket, msg.type, lof(tdata->TUser));
    //CONFIG DONE
    while (true) {
        msg = getMessage(tdata->socket);
        //refresh Friends table
        if (strcmp(msg.type, "F") == 0) {
            //send full list of friends
            sendMessage(tdata->socket, msg.type, lof(tdata->TUser));
        } else if (strcmp(msg.type, "S") == 0) {
            int exist = false;
            pthread_mutex_lock(&mutex);
            for (long unsigned int x = 0; x < tdata->tallUsers->size(); x++) {
                if (tdata->tallUsers->at(x)->getLogin().compare(msg.messagetext) == 0 &&
                    tdata->tallUsers->at(x)->getLogin().compare(tdata->TUser->getLogin()) != 0) {
                    exist = true;
                    break;
                }
            }
            pthread_mutex_unlock(&mutex);
            if (exist) {
                strcpy(msg.messagetext, "1");
                sendMessage(tdata->socket, msg.type, msg.messagetext);
            } else {
                strcpy(msg.messagetext, "0");
                sendMessage(tdata->socket, msg.type, msg.messagetext);
            }
        }
            //add new Friend
        else if (strcmp(msg.type, "FA") == 0) {
            pthread_mutex_lock(&mutex);
            for (long unsigned int x = 0; x < tdata->tallUsers->size(); x++) {
                if (tdata->tallUsers->at(x)->getLogin().compare(msg.messagetext) == 0) {
                    tdata->TUser->addNewFriend(tdata->tallUsers->at(x));
                    break;
                }
            }
            pthread_mutex_unlock(&mutex);
            strcpy(msg.type, "F");
            sendMessage(tdata->socket, msg.type, lof(tdata->TUser));
        } else if (strcmp(msg.type, "FD") == 0) {
            pthread_mutex_lock(&mutex);
            for (long unsigned int x = 0; x < tdata->TUser->getFriends().size(); x++) {
                if (tdata->TUser->getFriends()[x]->getLogin().compare(msg.messagetext) == 0) {
                    tdata->TUser->removeFriend(x);
                    break;
                }
            }
            pthread_mutex_unlock(&mutex);
            strcpy(msg.type, "F");
            sendMessage(tdata->socket, msg.type, lof(tdata->TUser));
        } else if (strcmp(msg.type, "C") == 0) {
            checkCall(tdata, msg);
        } else if (strcmp(msg.type, "Y") == 0) {
            string m;
            yes(tdata, msg.messagetext);
        } else if (strcmp(msg.type, "N") == 0) {
            no(tdata);
        } else if (strcmp(msg.type, "E") == 0) {
            strcpy(msg.type, "E");
            strcpy(msg.messagetext, "");
            pthread_mutex_lock(&mutex);
            tdata->TUser->setEnd(0);
            sendMessage(tdata->TUser->getWithWho()->getMysocket1(), msg.type, msg.messagetext);
            //tdata->TUser->getWithWho()->setWithWho(nullptr);
            //tdata->TUser->setWithWho(nullptr);
            pthread_mutex_unlock(&mutex);
        } else if (strcmp(msg.type, "LO") == 0) {
            break;
        } else if (strcmp(msg.type, "") == 0) {

            break;
        }
    }
}

void *ThreadBehavior(void *t_data) {
    struct message msg;
    string log;
    string pass;
    long unsigned int su;
    bool exist;
    bool end = false;
    struct t_data *tdata = (struct t_data *) t_data;
    while (!end) {
        exist = false;
        msg = getMessage(tdata->socket);
        if (strcmp(msg.type, "W") == 0) {
            videoStreamSocket(tdata, msg);
            end = true;
            // wideo streamconfig!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        } else {
            stringstream ss(msg.messagetext);
            getline(ss, log, '\n');
            getline(ss, pass, '\n');
            //Create new user!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (strcmp(msg.type, "R") == 0) {
                pthread_mutex_lock(&mutex);
                for (su = 0; su < tdata->tallUsers->size(); su++) {
                    if (tdata->tallUsers->at(su)->getLogin().compare(log) == 0)
                        exist = true;
                }
                if (!exist) {
                    tdata->tallUsers->push_back(new User(log, pass));
                    strcpy(msg.messagetext, "1");
                    sendMessage(tdata->socket, msg.type, msg.messagetext);
                    for (su = 0; su < tdata->tallUsers->size(); su++) {
                        if (tdata->tallUsers->at(su)->getLogin().compare(log) == 0) {
                            tdata->TUser = tdata->tallUsers->at(su);
                            break;
                        }
                    }
                    tdata->TUser->setMysocket1(tdata->socket);
                    pthread_mutex_unlock(&mutex);
                    logged(tdata);
                    pthread_mutex_lock(&mutex);
                    tdata->TUser->setLoged(false);
                    tdata->TUser->setMysocket1(-1);
                    pthread_mutex_unlock(&mutex);
                } else {//Resend to client info!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!}
                    pthread_mutex_unlock(&mutex);
                    strcpy(msg.messagetext, "0");
                    sendMessage(tdata->socket, msg.type, msg.messagetext);
                }
            }
                //log to !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            else if (strcmp(msg.type, "L") == 0) {

                pthread_mutex_lock(&mutex);
                for (su = 0; su < tdata->tallUsers->size(); su++) {
                    if (tdata->tallUsers->at(su)->getLogin().compare(log) == 0 &&
                        tdata->tallUsers->at(su)->getPassword().compare(pass) == 0 &&
                        !tdata->tallUsers->at(su)->isLoged()) {
                        tdata->TUser = tdata->tallUsers->at(su);
                        exist = true;
                        break;
                    }
                }
                if (exist) {
                    //resend that client is logged!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    tdata->TUser->setMysocket1(tdata->socket);
                    pthread_mutex_unlock(&mutex);
                    strcpy(msg.messagetext, "1");
                    sendMessage(tdata->socket, msg.type, msg.messagetext);
                    logged(tdata);
                    pthread_mutex_lock(&mutex);
                    tdata->TUser->setLoged(false);
                    tdata->TUser->setMysocket1(-1);
                    tdata->TUser->setMysocket2(-1);
                    pthread_mutex_unlock(&mutex);
                } else {
                    pthread_mutex_unlock(&mutex);
                    //resend that client isn't logged !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    strcpy(msg.messagetext, "0");
                    sendMessage(tdata->socket, msg.type, msg.messagetext);
                }
            } else {
                end = true;

            }
        }
    }

    pthread_exit(NULL);
}

class Server {
    int port = 1233;
    int queue = 1000;
public:
    vector<User *> *allUsers = new vector<User *>();

    void connectToServer() {

        int connection_socket_descriptor;
        int tr;
        int server_fd;
        struct sockaddr_in address;
        memset(&address, 0, sizeof(struct sockaddr));
        address.sin_family = AF_INET;
        address.sin_addr.s_addr = htonl(INADDR_ANY);
        address.sin_port = htons(port);
        if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
            perror("socket failed");
            exit(EXIT_FAILURE);
        }
        if (bind(server_fd, (struct sockaddr *) &address, sizeof(address)) < 0) {
            perror("bind failed");
            exit(EXIT_FAILURE);
        }
        if (listen(server_fd, queue) < 0) {
            perror("listen");
            exit(EXIT_FAILURE);
        }

        cout << "ready" << endl;
        while (true) {
            //thread creating
            connection_socket_descriptor = accept(server_fd, NULL, NULL);
            struct t_data *data = new t_data();
            data->socket = connection_socket_descriptor;
            data->tallUsers = allUsers;
            pthread_t *new_thread = new pthread_t();
            tr = pthread_create(new_thread, NULL, ThreadBehavior, (void *) data);
            if (tr < 0) {
                perror("Creating a thread failed");
                exit(-1);
            }
            pthread_detach(tr);
        }
    }
};

int main() {
    Server *server = new Server();
    server->connectToServer();
    return 0;
}