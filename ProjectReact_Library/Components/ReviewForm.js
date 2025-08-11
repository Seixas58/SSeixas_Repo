import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { URL_API } from '../context/Actions';

const ReviewForm = ({ isbn, title, onReviewSubmitted }) => {
    const [review, setReview] = useState('');
    const [recommended, setRecommended] = useState(false);

    const handleSubmit = async () => {
        if (!review.trim()) {
            Alert.alert('Erro', 'A avaliação não pode estar vazia.');
            return;
        }

        try {
            const response = await fetch(`${URL_API}/book/${isbn}/review`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    recommended,
                    review,
                }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error('Detalhes do Erro:', errorData);
                throw new Error(errorData.message || 'Erro ao submeter a avaliação.');
            }

            Alert.alert('Sucesso', 'Avaliação submetida com sucesso!');
            if (onReviewSubmitted) {
                onReviewSubmitted();
            }
        } catch (error) {
            console.error('Erro no HandleSubmit:', error.message); // Log do erro
            Alert.alert('Erro', error.message);
        }
    };
    return (
        <View style={styles.container}>
            <Text style={styles.title}>Avaliação para: {title}</Text>

            <TextInput
                style={styles.input}
                placeholder="Escreva sua avaliação"
                value={review}
                onChangeText={setReview}
                multiline
            />

            <View style={styles.checkboxContainer}>
                <Button
                    title={recommended ? 'Recomendado ✔️' : 'Recomendar este livro?'}
                    onPress={() => setRecommended(!recommended)}
                />
            </View>

            <Button title="Enviar Avaliação" onPress={handleSubmit} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        padding: 20,
        backgroundColor: '#FFFFFF',
        borderRadius: 10,
        elevation: 3,
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        marginBottom: 20,
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 16,
        color: '#333',
        textAlign: 'center',
        textTransform: 'uppercase',
        letterSpacing: 0.8,
    },
    input: {
        borderWidth: 1,
        borderColor: '#DDD',
        borderRadius: 8,
        padding: 12,
        marginBottom: 20,
        textAlignVertical: 'top',
        height: 120,
        backgroundColor: '#F9F9F9',
        fontSize: 16,
        color: '#333',
    },
    checkboxContainer: {
        marginBottom: 20,
        flexDirection: 'row',
        alignItems: 'center',
    },
    checkboxLabel: {
        fontSize: 16,
        color: '#333',
        marginLeft: 8,
    },
});

export default ReviewForm;
