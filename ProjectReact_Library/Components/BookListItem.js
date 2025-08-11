import React, {useContext, useState} from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Image,
  Modal,
  TextInput,
} from 'react-native';
import {Button} from 'react-native-paper';
import AppContext from '../context/AppContext';
import {URL_API} from '../context/Actions';
import {useNavigation} from "@react-navigation/native";
let stock;
const BookListItem = props => {
  const {item} = props;
  const [modalVisible, setModalVisible] = useState(false);
  const [stockValue, setStockValue] = useState('');
  const {state} = useContext(AppContext);
  const {libraryId} = state;

  const navigation = useNavigation();

  const toggleModal = () => {
    setModalVisible(!modalVisible);
  };

  const navigateToReview = () => {
    navigation.navigate('ReviewScreen', {isbn: item.isbn, title: item.title});
  };

  const handleAddStock = async () => {
    try {
      const stockResponse = await fetch(
        `${URL_API}/library/${libraryId}/book/${item.isbn}`,
      );

      if (!stockResponse.ok) {
        throw new Error('Error fetching stock');
      }

      const stockData = await stockResponse.json();
      stock = stockData.available ?? 0;

      let method = stock ? 'PUT' : 'POST';

      const apiUrl = `${URL_API}/library/${libraryId}/book/${item.isbn}`;

      const body = {stock: parseInt(stockValue) || 0};

      const response = await fetch(apiUrl, {
        method: method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
      });

      if (!response.ok) {
        throw new Error('Error adding stock');
      }

      console.log('Stock added successfully');

      toggleModal();
    } catch (error) {
      console.error('Error:', error);
    }
  };

  let imageUrl = item.cover.largeUrl.replace('/api', '');

  return (
    <TouchableOpacity style={styles.item} onPress={toggleModal}>
      <Text>ISBN: {item.isbn}</Text>
      <Text>TITLE: {item.title}</Text>
      <Text>DESCRIPTION: {item.description}</Text>
      <Text>DATE: {item.publishDate}</Text>
      <Image
        style={styles.tinyLogo}
        source={{uri: `http://193.136.62.24${imageUrl}`}}
      />

      <Button onPress={navigateToReview} style={styles.reviewButton}>
        Fazer Avaliação
      </Button>

      <Button
        onPress={() => navigation.navigate('ReviewsScreen', {isbn: item.isbn})}
        style={styles.reviewButton}>
        Ver Avaliações
      </Button>

      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}>
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <Text>
              Stock -{'>'} {stock}
            </Text>
            <Text>Add Stock</Text>
            <TextInput
              style={styles.input}
              placeholder="Enter Stock"
              value={stockValue}
              onChangeText={text => setStockValue(text)}
            />

            <Button onPress={handleAddStock}>Add Stock</Button>
            <Button onPress={toggleModal}>Close</Button>
          </View>
        </View>
      </Modal>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
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
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#E0E0E0',
    backgroundColor: '#F9F9F9',
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 22,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalView: {
    margin: 20,
    backgroundColor: '#FFFFFF',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.3,
    shadowRadius: 6,
    elevation: 5,
    width: '90%',
  },
  input: {
    borderColor: '#DDD',
    borderWidth: 1,
    backgroundColor: '#FFF',
    padding: 12,
    marginBottom: 15,
    width: '80%',
    borderRadius: 8,
    fontSize: 16,
    color: '#333',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 2,
  },
  reviewButton: {
    marginTop: 10,
    backgroundColor: '#007BFF',
    paddingVertical: 12,
    paddingHorizontal: 25,
    borderRadius: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 3,
    elevation: 4,
  },
  reviewButtonText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center',
    textTransform: 'uppercase',
    letterSpacing: 1,
  },
});

export default BookListItem;

