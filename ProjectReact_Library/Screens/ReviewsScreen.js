import React from 'react';
import { View, StyleSheet } from 'react-native';
import ReviewList from '../Components/ReviewList';
import { useRoute } from '@react-navigation/native';

const ReviewsScreen = () => {
    const route = useRoute();
    const { isbn } = route.params;

    return (
        <View style={styles.container}>
            <ReviewList isbn={isbn} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
});

export default ReviewsScreen;
