import React, { useContext } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Image } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const LibraryBookListItem = props => {
    const { item, libraryId } = props;
    const navigation = useNavigation();
    const imageUrl = item.book.cover.largeUrl.replace('/api', '');

    return (
        <View style={styles.item}>
            <Text>ISBN : {item.book.isbn} </Text>
            <Text>TITLE : {item.book.title} </Text>
            <Text>STOCK: {item.available} </Text>
            <Text>DATE : {item.book.publishDate} </Text>

            <Image
                style={styles.tinyLogo}
                source={{ uri: `http://193.136.62.24${imageUrl}` }}
            />

            <TouchableOpacity
                style={styles.button}
                onPress={() => {
                    console.log("Navigating to CheckoutBookScreen with:", {
                        libraryId: props.libraryId,
                        isbn: item.book.isbn,
                    });

                    navigation.navigate("CheckoutBookScreen", {
                        libraryId: props.libraryId,
                        bookId: item.book.isbn,
                    });
                }}
            >
                <Text style={styles.buttonText}>Checkout</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    button: {
        alignItems: 'center',
        justifyContent: 'center',
        paddingVertical: 10,
        paddingHorizontal: 20,
        backgroundColor: '#007BFF',
        borderRadius: 8,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 3,
        elevation: 4,
        marginTop: 15,
    },
    buttonText: {
        color: '#FFF',
        fontSize: 16,
        fontWeight: 'bold',
    },
    item: {
        borderTopWidth: 1,
        borderBottomWidth: 1,
        borderColor: '#E0E0E0',
        backgroundColor: '#FFFFFF',
        padding: 15,
        marginVertical: 10,
        borderRadius: 8,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },
    tinyLogo: {
        marginTop: 15,
        marginBottom: 10,
        alignSelf: 'center',
        width: 200,
        height: 300,
        borderRadius: 8,
        borderWidth: 1,
        borderColor: '#E0E0E0',
        backgroundColor: '#F9F9F9',
    },
});

export default LibraryBookListItem;
