import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator } from 'react-native';
import { URL_API } from '../context/Actions';

const ReviewList = ({ isbn }) => {
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const response = await fetch(`${URL_API}/book/${isbn}/review`);
                if (!response.ok) {
                    throw new Error('Erro ao buscar as avaliações.');
                }
                const data = await response.json();
                setReviews(data);
            } catch (error) {
                console.error('Erro ao buscar as avaliações:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchReviews();
    }, [isbn]);

    if (loading) {
        return <ActivityIndicator size="large" color="#0000ff" />;
    }

    if (reviews.length === 0) {
        return (
            <View style={styles.noReviewsContainer}>
                <Text style={styles.noReviewsText}>Nenhuma avaliação encontrada.</Text>
            </View>
        );
    }

    const renderReview = ({ item }) => (
        <View style={styles.reviewItem}>
            <Text style={styles.reviewer}>Autor: {item.reviewer || 'Anônimo'}</Text>
            <Text style={styles.reviewText}>Avaliação: {item.review}</Text>
            <Text style={styles.recommended}>
                Recomenda? {item.recommended ? 'Sim' : 'Não'}
            </Text>
            <Text style={styles.date}>Data: {new Date(item.createdDate).toLocaleDateString()}</Text>
        </View>
    );

    return (
        <FlatList
            data={reviews}
            renderItem={renderReview}
            keyExtractor={(item) => item.id}
            contentContainerStyle={styles.listContainer}
        />
    );
};

const styles = StyleSheet.create({
    listContainer: {
        padding: 20,
        backgroundColor: '#FFFFFF',
    },
    reviewItem: {
        padding: 16,
        marginBottom: 16,
        backgroundColor: '#F9F9F9',
        borderRadius: 10,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 2,
    },
    reviewer: {
        fontWeight: 'bold',
        fontSize: 18,
        color: '#333',
        marginBottom: 8,
    },
    reviewText: {
        marginVertical: 8,
        fontSize: 16,
        color: '#555',
        lineHeight: 22,
    },
    recommended: {
        fontSize: 14,
        color: '#007BFF',
        fontStyle: 'italic',
        marginTop: 8,
    },
    date: {
        fontSize: 12,
        color: '#AAA',
        marginTop: 4,
    },
    noReviewsContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F9F9F9',
        padding: 20,
    },
    noReviewsText: {
        fontSize: 18,
        color: '#666',
        textAlign: 'center',
        fontStyle: 'italic',
    },
});

export default ReviewList;
