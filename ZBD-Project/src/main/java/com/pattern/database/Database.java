package com.pattern.database;

import com.pattern.database.tables.*;

import java.util.ArrayList;
import java.sql.*;

public class Database {
    Connection con;
    private ArrayList<Characters> characters = new ArrayList<Characters>();
    private ArrayList<CharPet> charPets = new ArrayList<CharPet>();
    private ArrayList<CharQuests> charQuests = new ArrayList<CharQuests>();
    private ArrayList<Equipments> equipments = new ArrayList<Equipments>();
    private ArrayList<Items> items = new ArrayList<Items>();
    private ArrayList<Maps> maps = new ArrayList<Maps>();
    private ArrayList<Npcs> npcs = new ArrayList<Npcs>();
    private ArrayList<Pets> pets = new ArrayList<Pets>();
    private ArrayList<Quests> quests = new ArrayList<Quests>();
    private ArrayList<Servers> servers = new ArrayList<Servers>();
    private ArrayList<Users> users = new ArrayList<Users>();

    public Database() {
        connect();
        loadUsers();
        loadServers();
        loadQuests();
        loadPets();
        loadNPCS();
        loadMaps();
        loadItems();
        loadEquipments();
        loadCharacters();
        loadChar_Quests();
        loadChar_Pet();
    }

    public void connect() {
        try {
            //step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

//step2 create  the connection object
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl", "inf136741", "inf136741");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void disconnect() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//test


    //----------------------------------Updaters------------------------------------------------------------------------
    public void updateUsers(int id,String email, String login, String pass)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE USERS SET EMAIL=?,LOGIN=?,PASS=? where ID_USER=?");
            pstmt.setString(1,email);
            pstmt.setString(2,login);
            pstmt.setString(3,pass);
            pstmt.setInt(4,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadUsers();
    }
    public void updateServers(int id,String name, int aoc)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE SERVERS SET NAME=?,AMOUNT_OF_CHARACTER=? where ID_SERVER=?");
            pstmt.setString(1,name);
            pstmt.setInt(2,aoc);
            pstmt.setInt(3,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadServers();
    }
    public void updateQuests(int id,String name, String txtfile,int minlvl,int id_npc)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE QUESTS SET NAME=?,TXT_FILE=?,MIN_LEVEL=?,ID_NPC=? WHERE ID_QUEST=?");
            pstmt.setString(1,name);
            pstmt.setString(2,txtfile);
            pstmt.setInt(3,minlvl);
            pstmt.setInt(4,id_npc);
            pstmt.setInt(5,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadQuests();
    }
    public void updatePet(int id,String name, int Attack,int Defence)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE PETS SET PET_NAME=?,ATTACK=?,DEFENCE=? WHERE ID_PET=?");
            pstmt.setString(1,name);
            pstmt.setInt(2,Attack);
            pstmt.setInt(3,Defence);
            pstmt.setInt(4,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadPets();
    }
    public void updateNPC(int id,String name, int id_map,int X_pos,int Y_pos,int Z_pos)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE NPCS SET NAME=?,ID_MAP=?,X_POSITION=?,Y_POSTITION=?,Z_POSITION=?  WHERE ID_NPC=?");
            pstmt.setString(1,name);
            pstmt.setInt(2,id_map);
            pstmt.setInt(3,X_pos);
            pstmt.setInt(4,Y_pos);
            pstmt.setInt(5,Z_pos);
            pstmt.setInt(6,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadNPCS();
    }
    public void updateMaps(int id,String name, String file)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE MAPS SET MAP_NAME=?,MAP_FILE=? WHERE ID_MAP=?");
            pstmt.setString(1,name);
            pstmt.setString(2,file);
            pstmt.setInt(3,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadMaps();
    }
    public void updateItems(int id,String name, String Type,int Attack,int Defence)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE ITEMS SET NAME=?,TYPE=?,ATTACK=?,DEFENCE=? WHERE ID_ITEM=?");
            pstmt.setString(1, name);
            pstmt.setString(2, Type);
            pstmt.setInt(3, Attack);
            pstmt.setInt(4, Defence);
            pstmt.setInt(5,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadItems();
    }
    public void updateEq(int id,int ID_CHAR,int ID_ITEM,String name,int LEVEL_OF_UPGRADE,int BONUS_ATTACK,int bonus_defence,int WEARED)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE EQUIPMENTS SET ID_CHAR=?,ID_ITEM=?,EQ_NAME=?,LEVEL_OF_UPGRADE=?" +
                    ",BONUS_ATTACK=?,BONUS_DEFENCE=?,WEARED=? WHERE ID_CHAR_ITEM=?");

            pstmt.setInt(1, ID_CHAR);
            pstmt.setInt(2, ID_ITEM);
            pstmt.setString(3,name);
            pstmt.setInt(4, LEVEL_OF_UPGRADE);
            pstmt.setInt(5, BONUS_ATTACK);
            pstmt.setInt(6,bonus_defence);
            pstmt.setInt(7, WEARED);
            pstmt.setInt(8,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadEquipments();
    }
    public void updateCharacters(int id,String Name, int ID_User,String Class,String Gender,int CHARLVL,int Experience,int ID_SERVER,int ATTACK,int DEFENCE,int ID_MAP)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE CHARACTERS SET NAME=?,ID_USER=?,CLASS=?,GENDER=?," +
                    "CHARLVL=?,EXPERIENCE=?,ID_SERVER=?,ATTACK=?,DEFENCE=?,ID_MAP=? WHERE ID_CHAR=?");

            pstmt.setString(1, Name);
            pstmt.setInt(2, ID_User);
            pstmt.setString(3, Class);
            pstmt.setString(4, Gender);
            pstmt.setInt(5, CHARLVL);
            pstmt.setInt(6, Experience);
            pstmt.setInt(7, ID_SERVER);
            pstmt.setInt(8, ATTACK);
            pstmt.setInt(9, DEFENCE);
            pstmt.setInt(10, ID_MAP);
            pstmt.setInt(11,id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadCharacters();
    }
    public void updateCharQuest(int id,int idQuest, int idChar, String status)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("update CHAR_QUESTS set ID_QUEST=?,ID_CHAR=?,STATUS=? where ID_CHAR_QUESTS=?");
            pstmt.setInt(1,idQuest );
            pstmt.setInt(2, idChar);
            pstmt.setString(3, status);
            pstmt.setInt(4, id);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadChar_Pet();
    }
    public void updateCHAR_PET(int ID_CHAR_PET,int ID_PET,int ID_CHAR,String name,int PET_LEVEL,int EXPERIENCE,int BONUS_ATTACK,int BONUS_DEFENCE)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE CHAR_PET SET ID_PET=?,ID_CHAR=?,CHAR_PET_NAME=?,PET_LEVEL=?,EXPERIENCE=?,BONUS_ATTACK=?" +
                    ",BONUS_DEFENCE=? WHERE ID_CHAR_PET=?");
            pstmt.setInt(1, ID_PET);
            pstmt.setInt(2, ID_CHAR);
            pstmt.setString(3, name);
            pstmt.setInt(5, PET_LEVEL);
            pstmt.setInt(5, EXPERIENCE);
            pstmt.setInt(6, BONUS_ATTACK);
            pstmt.setInt(7, BONUS_DEFENCE);
            pstmt.setInt(8,ID_CHAR_PET);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadChar_Pet();
    }

    //----------------------------------Inserters-----------------------------------------------------------------------
    public void insertUsers(String email, String login, String pass)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("call newuser(?,?,?)");
            pstmt.setString(1,email);
            pstmt.setString(2,login);
            pstmt.setString(3,pass);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadUsers();
    }
    public void insertServers(String name, int aoc)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into SERVERS(NAME,AMOUNT_OF_CHARACTER) VALUES (?,?)");
            pstmt.setString(1,name);
            pstmt.setInt(2,aoc);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadServers();
    }
    public void insertQuests(String name, String txtfile,int minlvl,int id_npc)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into QUESTS(NAME,TXT_FILE,MIN_LEVEL,ID_NPC) VALUES (?,?,?,?)");
            pstmt.setString(1,name);
            pstmt.setString(2,txtfile);
            pstmt.setInt(3,minlvl);
            pstmt.setInt(4,id_npc);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadQuests();
    }
    public void insertPet(String name, int Attack,int Defence)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into PETS(PET_NAME,ATTACK,DEFENCE) VALUES (?,?,?)");
            pstmt.setString(1,name);
            pstmt.setInt(2,Attack);
            pstmt.setInt(3,Defence);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadPets();
    }
    public void insertNPC(String name, int id_map,int X_pos,int Y_pos,int Z_pos)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into NPCS(NAME,ID_MAP,X_POSITION,Y_POSTITION,Z_POSITION) VALUES (?,?,?,?,?)");
            pstmt.setString(1,name);
            pstmt.setInt(2,id_map);
            pstmt.setInt(3,X_pos);
            pstmt.setInt(4,Y_pos);
            pstmt.setInt(5,Z_pos);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadNPCS();
    }
    public void insertMaps(String name, String file)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into MAPS(MAP_NAME,MAP_FILE) VALUES (?,?)");
            pstmt.setString(1,name);
            pstmt.setString(2,file);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadMaps();
    }
    public void insertItems(String name, String Type,int Attack,int Defence)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into ITEMS(NAME,TYPE,ATTACK,DEFENCE) VALUES (?,?,?,?)");
            pstmt.setString(1, name);
            pstmt.setString(2, Type);
            pstmt.setInt(3, Attack);
            pstmt.setInt(4, Defence);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadItems();
    }
    public void insertEq(int idChar, int idItem,String Eq_name, int levelOfUpgrade, int bonusAttack, int bonusDefence, int weared)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into EQUIPMENTS(ID_CHAR,ID_ITEM,EQ_NAME,LEVEL_OF_UPGRADE,BONUS_ATTACK,BONUS_DEFENCE,WEARED) VALUES (?,?,?,?,?,?,?)");

            pstmt.setInt(1, idChar);
            pstmt.setInt(2, idItem);
            pstmt.setString(3, Eq_name);
            pstmt.setInt(4, levelOfUpgrade);
            pstmt.setInt(5, bonusAttack);
            pstmt.setInt(6, bonusDefence);
            pstmt.setInt(7,weared);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadEquipments();
    }
    public void insertCharacters(String Name, int ID_User,String Class,String Gender,int CHARLVL,int Experience,int ID_SERVER,int ATTACK,int DEFENCE,int ID_MAP)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into CHARACTERS(NAME,ID_USER,CLASS,GENDER,CHARLVL,EXPERIENCE,ID_SERVER,ATTACK,DEFENCE,ID_MAP) VALUES (?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, Name);
            pstmt.setInt(2, ID_User);
            pstmt.setString(3, Class);
            pstmt.setString(4, Gender);
            pstmt.setInt(5, CHARLVL);
            pstmt.setInt(6, Experience);
            pstmt.setInt(7, ID_SERVER);
            pstmt.setInt(8, ATTACK);
            pstmt.setInt(9, DEFENCE);
            pstmt.setInt(10, ID_MAP);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("I didnt add character");
        }
        loadCharacters();
    }
    //char_QUEST BRAK KLUCZA PRYWATNEGO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void insertCharQuest(int idQuest, int idChar, String status)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into CHAR_QUESTS(ID_QUEST,ID_CHAR,STATUS) VALUES (?,?,?)");
            pstmt.setInt(1,idQuest );
            pstmt.setInt(2, idChar);
            pstmt.setString(3, status);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadChar_Pet();
    }
    public void insertCharPet(int ID_PET,int ID_CHAR,String name,int PET_LEVEL,int EXPERIENCE,int BONUS_ATTACK,int BONUS_DEFENCE)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT into CHAR_PET(ID_PET,ID_CHAR,CHAR_PET_NAME,PET_LEVEL,EXPERIENCE,BONUS_ATTACK,BONUS_DEFENCE) VALUES (?,?,?,?,?,?,?)");
            pstmt.setInt(1, ID_PET);
            pstmt.setInt(2, ID_CHAR);
            pstmt.setString(3, name);
            pstmt.setInt(4, PET_LEVEL);
            pstmt.setInt(5, EXPERIENCE);
            pstmt.setInt(6, BONUS_ATTACK);
            pstmt.setInt(7, BONUS_DEFENCE);
            System.out.println(pstmt);
            pstmt.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        loadChar_Pet();
    }



    //----------------------------------Removers------------------------------------------------------------------------
    public void deleteUsers(int ID)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM USERS where users.ID_USER = ? ");
            pstmt.setInt(1, ID);
            pstmt.execute();
            System.out.println("dupa");
            loadUsers();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteServers(int ID_SERVER)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM SERVERS where SERVERS.ID_SERVER = ?");
            pstmt.setInt(1, ID_SERVER);
            pstmt.execute();
            loadServers();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteQuests(int ID_Quests)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM QUESTS where QUESTS.ID_QUEST = ?");
            pstmt.setInt(1, ID_Quests);
            pstmt.execute();
            loadQuests();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deletePets(int ID_Pet)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM PETS where PETS.ID_PET = ?");
            pstmt.setInt(1, ID_Pet);
            pstmt.execute();
            loadPets();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteNPCS(int ID_NPC)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM NPCS where NPCS.ID_NPC = ?");
            pstmt.setInt(1, ID_NPC);
            pstmt.execute();
            loadNPCS();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteMaps(int ID_Map)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM MAPS where MAPS.ID_MAP = ?");
            pstmt.setInt(1, ID_Map);
            System.out.println(pstmt);
            pstmt.execute();
            loadMaps();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteItems(int ID_Item)
    {
        try {

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM ITEMS where ITEMS.ID_ITEM = ? ");
            pstmt.setInt(1, ID_Item);
            pstmt.execute();
            loadItems();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteEq(int ID_Eq)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM EQUIPMENTS where EQUIPMENTS.ID_CHAR_ITEM = ?");
            pstmt.setInt(1, ID_Eq);
            pstmt.execute();
            loadEquipments();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void deleteChar(int ID_Char)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM CHARACTERS where CHARACTERS.ID_CHAR = ?");
            pstmt.setInt(1, ID_Char);
            pstmt.execute();
            loadCharacters();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteCharQuest(int ID_Q)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM CHAR_QUESTS where ID_CHAR_QUESTS = ?");
            pstmt.setInt(1, ID_Q);
            System.out.println(pstmt);
            pstmt.execute();
            loadChar_Quests();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteCharPet(int ID_CP)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM CHAR_PET where ID_CHAR_PET == ?");
            pstmt.setInt(1, ID_CP);
            pstmt.execute();
            loadChar_Pet();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    //----------------------------------Loaders-------------------------------------------------------------------------
    public void loadUsers() {
        try {
            if (users.size() > 0)
                users.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from USERS");
            while (rs.next())
                users.add(new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadServers() {
        try {
            if (servers.size() > 0)
                servers.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from SERVERS");
            while (rs.next())
                servers.add(new Servers(rs.getString(1), rs.getInt(2), rs.getInt(3)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadQuests() {
        try {
            if (quests.size() > 0)
                quests.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from QUESTS");
            while (rs.next())
                quests.add(new Quests(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadPets() {
        try {
            if (pets.size() > 0)
                pets.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from PETS");
            while (rs.next())
                pets.add(new Pets(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadNPCS() {
        try {
            if (npcs.size() > 0)
                npcs.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from NPCS");
            while (rs.next())
                npcs.add(new Npcs(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadMaps() {
        try {
            if (maps.size() > 0)
                maps.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from MAPS");
            while (rs.next())
                maps.add(new Maps(rs.getInt(1), rs.getString(2), rs.getString(3)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadItems() {
        try {
            if (items.size() > 0)
                items.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ITEMS");
            while (rs.next())
                items.add(new Items(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadEquipments() {
        try {
            if (equipments.size() > 0)
                equipments.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from EQUIPMENTS");
            while (rs.next())
                equipments.add(new Equipments(rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadCharacters() {
        try {
            if (characters.size() > 0)
                characters.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CHARACTERS");
            while (rs.next())
                characters.add(new Characters(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadChar_Quests() {
        try {
            if (charQuests.size() > 0)
                charQuests.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CHAR_QUESTS");
            while (rs.next())
                charQuests.add(new CharQuests(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4 )));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void loadChar_Pet() {
        try {
            if (charPets.size() > 0)
                charPets.clear();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CHAR_PET");
            while (rs.next())

                charPets.add(new CharPet(rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //----------------------------------Getters and Setters-------------------------------------------------------------
    public ArrayList<Characters> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Characters> characters) {
        this.characters = characters;
    }

    public ArrayList<CharPet> getCharPets(int id) {
        ArrayList<CharPet> returnedPets = new ArrayList<>();
        for(CharPet pet : charPets) {
            if (pet.getIdChar() == id) {
                returnedPets.add(pet);
            }
        }
        return returnedPets;
    }

    public void setCharPets(ArrayList<CharPet> charPets) {
        this.charPets = charPets;
    }

    public ArrayList<CharQuests> getCharQuests() {
        return charQuests;
    }

    public void setCharQuests(ArrayList<CharQuests> charQuests) {
        this.charQuests = charQuests;
    }

    public ArrayList<Equipments> getEquipments(int id) {
        ArrayList<Equipments> returnedEq = new ArrayList<>();
        for(Equipments eq : equipments) {
            if (eq.getIdChar() == id) {
                returnedEq.add(eq);
            }
        }
        return returnedEq;
    }

    public void setEquipments(ArrayList<Equipments> equipments) {
        this.equipments = equipments;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public ArrayList<Maps> getMaps() {
        return maps;
    }

    public void setMaps(ArrayList<Maps> maps) {
        this.maps = maps;
    }

    public ArrayList<Npcs> getNpcs() {
        return npcs;
    }

    public void setNpcs(ArrayList<Npcs> npcs) {
        this.npcs = npcs;
    }

    public ArrayList<Pets> getPets() {
        return pets;
    }

    public void setPets(ArrayList<Pets> pets) {
        this.pets = pets;
    }

    public ArrayList<Quests> getQuests() {
        return quests;
    }

    public void setQuests(ArrayList<Quests> quests) {
        this.quests = quests;
    }

    public ArrayList<Servers> getServers() {
        return servers;
    }

    public void setServers(ArrayList<Servers> servers) {
        this.servers = servers;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }

    public Characters getCharById(int id) {

        for(Characters character : characters) {
            if (character.getIdChar() == id) {
                return character;
            }
        }
        //this will never reached
        return new Characters(-1,"not exist",-1,"Warrior","Male",-1,-1,-1,-1,-1,-1);
    }

    public CharPet getCharPetById(int id) {

        for(CharPet pet : charPets) {
            if (pet.getIdChar() == id) {
                return pet;
            }
        }
        //this will never reached
        return new CharPet(-1,-1,-1,"none",-1,-1,-1,-1);
    }

    public Equipments getEqById(int id) {

        for(Equipments item : equipments) {
            if (item.getIdChar() == id) {
                return item;
            }
        }
        //this will never reached
        return new Equipments(-1,-1,-1,"none",-1,-1,-1,-1);
    }


    public Items getItemById(int id) {

        for(Items item : items) {
            if (item.getIdItem() == id) {
                return item;
            }
        }
        //this will never reached
        return new Items(-1, "something went wrong", "something went wrong", -1, -1);
    }
}
