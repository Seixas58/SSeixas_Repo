import React from "react";
import { View, StyleSheet } from "react-native";
import UpdateLibrary from "../Components/UpdateLibrary";

const UpdateLibraryScreen = ({ route, navigation }) => {
    const { library } = route.params;

    const handleUpdateSuccess = () => {
        navigation.goBack();
    };

    return (
        <View style={styles.container}>
            <UpdateLibrary library={library} onSuccess={handleUpdateSuccess} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#F9F9F9",
        padding: 20,
    },
});

export default UpdateLibraryScreen;
