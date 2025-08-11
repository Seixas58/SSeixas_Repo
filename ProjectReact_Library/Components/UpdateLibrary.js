import React, { useState } from "react";
import { View, Text, TextInput, StyleSheet, Alert } from "react-native";
import { Button } from "react-native-paper";
import { URL_API } from "../context/Actions";

const UpdateLibrary = ({ library, onSuccess }) => {
  const [name, setName] = useState(library.name);
  const [address, setAddress] = useState(library.address);
  const [open, setOpen] = useState(library.open.toString());
  const [openDays, setOpenDays] = useState(library.openDays);
  const [openTime, setOpenTime] = useState(library.openTime);
  const [closeTime, setCloseTime] = useState(library.closeTime);
  const [openStatement, setOpenStatement] = useState(library.openStatement);

  const handleUpdate = async () => {
    const apiUrl = `${URL_API}/library/${library.id}`;
    const updatedData = {
      name,
      address,
      open: open === "true",
      openDays,
      openTime,
      closeTime,
      openStatement,
    };

    try {
      const response = await fetch(apiUrl, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedData),
      });

      if (!response.ok) {
        throw new Error("Error updating library.");
      }

      Alert.alert("Success", "Library updated successfully!");
      if (onSuccess) onSuccess();
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Failed to update library.");
    }
  };

  return (
      <View style={styles.container}>
        <Text style={styles.title}>Update Library</Text>
        <TextInput
            style={styles.input}
            placeholder="Name"
            value={name}
            onChangeText={setName}
        />
        <TextInput
            style={styles.input}
            placeholder="Address"
            value={address}
            onChangeText={setAddress}
        />
        <TextInput
            style={styles.input}
            placeholder="Open (true/false)"
            value={open}
            onChangeText={setOpen}
        />
        <TextInput
            style={styles.input}
            placeholder="Open Days"
            value={openDays}
            onChangeText={setOpenDays}
        />
        <TextInput
            style={styles.input}
            placeholder="Open Time"
            value={openTime}
            onChangeText={setOpenTime}
        />
        <TextInput
            style={styles.input}
            placeholder="Close Time"
            value={closeTime}
            onChangeText={setCloseTime}
        />
        <TextInput
            style={styles.input}
            placeholder="Open Statement"
            value={openStatement}
            onChangeText={setOpenStatement}
        />
        <Button mode="contained" onPress={handleUpdate} style={styles.updateButton}>
          Confirm Update
        </Button>
      </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 20,
    backgroundColor: "#F9F9F9",
    borderRadius: 8,
    elevation: 3,
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center",
  },
  input: {
    borderWidth: 1,
    borderColor: "#DDD",
    borderRadius: 8,
    padding: 10,
    marginBottom: 15,
    backgroundColor: "#FFF",
  },
  updateButton: {
    backgroundColor: "#007BFF",
    paddingVertical: 10,
  },
});

export default UpdateLibrary;
