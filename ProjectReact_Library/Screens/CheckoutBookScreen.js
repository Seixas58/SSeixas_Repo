import React from 'react';
import { View, Text, StyleSheet, Alert } from 'react-native';

const CheckoutBookScreen = ({ route, navigation }) => {
    const { libraryId, bookId: isbn } = route.params || {};

    if (!libraryId || !isbn) {
        console.error("Missing parameters:", { libraryId, isbn });
        Alert.alert("Error", "Library or Book ISBN is missing.");
        navigation.goBack();
        return null;
    }

    console.log("Library ID:", libraryId);
    console.log("ISBN:", isbn);

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Checkout Book</Text>
            <Text>Library ID: {libraryId}</Text>
            <Text>ISBN: {isbn}</Text>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
        backgroundColor: '#F9F9F9',
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 20,
    },
});

export default CheckoutBookScreen;
