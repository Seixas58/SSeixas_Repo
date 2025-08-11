import React, { useContext, useEffect, useState } from "react";
import {View, Text, TouchableOpacity, StyleSheet} from 'react-native';
import AppContext from '../context/AppContext';
import {changeLibraryId, fetchLibraries, fetchLibrariesStarted, URL_API} from "../context/Actions";
import {useNavigation} from '@react-navigation/native';
import {Button} from "react-native-paper";

const LibraryListItem = props => {
  const navigation = useNavigation();
  const {item} = props;
  const {state, dispatch} = useContext(AppContext);

    async function handlePress  (libraryId)  {
        const apiUrl = `${URL_API}/library/${libraryId}`;
        console.log(apiUrl)
        try {
            const response = await fetch(apiUrl, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Error deleting library.');
            }
            const url = `${URL_API}/library`;
            dispatch(fetchLibrariesStarted());
            const request = {};
            fetchLibraries(url, request, dispatch);

            console.log('Library removed successfully.');
        }catch (error) {
            console.log(error);
        }

    };
  return (
    <View style={styles.item}>
        <TouchableOpacity
            onPress={() => {
                navigation.navigate('Library Books', { libraryId: item.id });
            }}>
            <Text style={styles.titleText}>Library {item.name}</Text>
        </TouchableOpacity>
      <Text>ADDRESS : {item.address} </Text>
      <Text>OPEN : {item.open} </Text>
      <Text>OPENDAYS : {item.openDays} </Text>
      <Text>OPENTIME: {item.openTime} </Text>
      <Text>CLOSETIME : {item.closeTime} </Text>
      <Text>OPENSTATEMENT : {item.openStatement} </Text>
        <Text>ID : {item.id} </Text>
        <Button
            onPress={() =>
                navigation.navigate("UpdateLibraryScreen", { library: item })
            }
            mode="contained"
            style={styles.updateButton}
        >
            Update
        </Button>
        <Button onPress={() => handlePress(item.id)}>
            Delete
        </Button>
    </View>
  );
};

const styles = StyleSheet.create({
    item: {
        backgroundColor: "#F9F9F9",
        borderRadius: 8,
        padding: 15,
        marginVertical: 10,
        marginHorizontal: 20,
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },
    titleText: {
        fontSize: 22,
        fontWeight: "bold",
        color: "#333",
        marginBottom: 10,
    },
    infoText: {
        fontSize: 16,
        color: "#555",
        marginBottom: 5,
    },
    deleteButton: {
        marginTop: 15,
        alignSelf: "flex-start",
    },
});
export default LibraryListItem;
