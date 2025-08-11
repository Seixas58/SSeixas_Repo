import React from 'react';
import { View, StyleSheet } from 'react-native';
import ReviewForm from '../Components/ReviewForm';
import { useRoute, useNavigation } from '@react-navigation/native';

const ReviewScreen = () => {
    const route = useRoute();
    const navigation = useNavigation();
    const { isbn, title } = route.params;

    return (
        <View style={styles.container}>
            <ReviewForm
                isbn={isbn}
                title={title}
                onReviewSubmitted={() => navigation.goBack()}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 16,
        backgroundColor: '#f5f5f5',
    },
});

export default ReviewScreen;
