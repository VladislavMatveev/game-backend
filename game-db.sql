PGDMP         7    
            z            postgres    14.2    14.2     9           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            :           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ;           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            <           1262    13754    postgres    DATABASE     l   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE postgres;
                postgres    false            =           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    3388                        2615    16540    game    SCHEMA        CREATE SCHEMA game;
    DROP SCHEMA game;
                postgres    false            ?            1259    16549    desks    TABLE     k  CREATE TABLE game.desks (
    id bigint NOT NULL,
    session_id bigint NOT NULL,
    f1 character(1) DEFAULT 1,
    f2 character(1) DEFAULT 2,
    f3 character(1) DEFAULT 3,
    f4 character(1) DEFAULT 4,
    f5 character(1) DEFAULT 5,
    f6 character(1) DEFAULT 6,
    f7 character(1) DEFAULT 7,
    f8 character(1) DEFAULT 8,
    f9 character(1) DEFAULT 9
);
    DROP TABLE game.desks;
       game         heap    postgres    false    6            ?            1259    16564    desks_id_seq    SEQUENCE     ?   ALTER TABLE game.desks ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game.desks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            game          postgres    false    6    232            ?            1259    16557    moves    TABLE     ?   CREATE TABLE game.moves (
    id bigint NOT NULL,
    session_id bigint NOT NULL,
    move text,
    move_by text NOT NULL,
    move_date timestamp without time zone NOT NULL,
    move_sign text
);
    DROP TABLE game.moves;
       game         heap    postgres    false    6            ?            1259    16565    moves_id_seq    SEQUENCE     ?   ALTER TABLE game.moves ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game.moves_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1
);
            game          postgres    false    6    233            ?            1259    16541    sessions    TABLE     f   CREATE TABLE game.sessions (
    id bigint NOT NULL,
    first_move boolean,
    uid text NOT NULL
);
    DROP TABLE game.sessions;
       game         heap    postgres    false    6            ?            1259    16546    users_id_seq    SEQUENCE     ?   ALTER TABLE game.sessions ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME game.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1
);
            game          postgres    false    6    230            3          0    16549    desks 
   TABLE DATA           Q   COPY game.desks (id, session_id, f1, f2, f3, f4, f5, f6, f7, f8, f9) FROM stdin;
    game          postgres    false    232   .       4          0    16557    moves 
   TABLE DATA           R   COPY game.moves (id, session_id, move, move_by, move_date, move_sign) FROM stdin;
    game          postgres    false    233   `       1          0    16541    sessions 
   TABLE DATA           5   COPY game.sessions (id, first_move, uid) FROM stdin;
    game          postgres    false    230   ?       >           0    0    desks_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('game.desks_id_seq', 74, true);
          game          postgres    false    234            ?           0    0    moves_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('game.moves_id_seq', 129, true);
          game          postgres    false    235            @           0    0    users_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('game.users_id_seq', 78, true);
          game          postgres    false    231            ?           2606    16556    desks desks_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY game.desks
    ADD CONSTRAINT desks_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY game.desks DROP CONSTRAINT desks_pkey;
       game            postgres    false    232            ?           2606    16563    moves moves_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY game.moves
    ADD CONSTRAINT moves_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY game.moves DROP CONSTRAINT moves_pkey;
       game            postgres    false    233            ?           2606    16545    sessions users_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY game.sessions
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 ;   ALTER TABLE ONLY game.sessions DROP CONSTRAINT users_pkey;
       game            postgres    false    230            ?           2606    16576    moves session_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY game.moves
    ADD CONSTRAINT session_fkey FOREIGN KEY (session_id) REFERENCES game.sessions(id) ON DELETE CASCADE NOT VALID;
 :   ALTER TABLE ONLY game.moves DROP CONSTRAINT session_fkey;
       game          postgres    false    3229    230    233            ?           2606    16581    desks session_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY game.desks
    ADD CONSTRAINT session_fkey FOREIGN KEY (session_id) REFERENCES game.sessions(id) ON DELETE CASCADE NOT VALID;
 :   ALTER TABLE ONLY game.desks DROP CONSTRAINT session_fkey;
       game          postgres    false    230    232    3229            3   "   x?37?4??? BcNNS 6?????????? E?E      4   b   x?u?1?  ????@?l?.????d??????#???sFf?jY?4!?(?!?=?y??r?S????$?J
Σo%c?c????ssƘ#       1   7   x?3??,?L20656N2ҵ0?L?5?L?еHNI?M31014IM510J?????? ?
?     