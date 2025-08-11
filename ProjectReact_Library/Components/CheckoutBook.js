import React from 'react';
import { View, Text, StyleSheet, Alert, TouchableOpacity } from 'react-native';
import { Button } from 'react-native-paper';
import { URL_API } from '../context/Actions';

const CheckoutBook = ({ libraryId, bookId, onSuccess }) => {
    const handleCheckout = async () => {
        const apiUrl = `${URL_API}/library/${libraryId}/book/${bookId}/checkout`;

        console.log(`API URL: ${apiUrl}`);
        console.log(`Library ID: ${libraryId}, Book ID: ${bookId}`);

        try {
            const response = await fetch(apiUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            console.log('Response Status:', response.status);

            if (!response.ok) {
                const errorBody = await response.text();
                console.log('Response Error Body:', errorBody);
                throw new Error('Failed to checkout book.');
            }

            Alert.alert('Success', 'Book checked out successfully!');
            if (onSuccess) onSuccess();
        } catch (error) {
            console.error('Error:', error.message);
            Alert.alert('Error', 'Failed to checkout the book.');
        }
    };


    return (
        <View style={styles.container}>
            <Text style={styles.text}>
                Are you sure you want to checkout this book?
            </Text>
            <TouchableOpacity
                style={styles.button}
                onPress={() =>
                    navigation.navigate("CheckoutBookScreen", {
                        libraryId: props.libraryId,
                        bookId: item.book.id,
                    })
                }
            >
                <Text style={styles.buttonText}>Checkout</Text>
            </TouchableOpacity>

        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        padding: 20,
        backgroundColor: '#FFF',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
        alignItems: 'center',
    },
    text: {
        fontSize: 16,
        marginBottom: 20,
        color: '#333',
        textAlign: 'center',
    },
    button: {
        backgroundColor: '#007BFF',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 8,
    },
    buttonText: {
        color: '#FFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
});

export default CheckoutBook;
