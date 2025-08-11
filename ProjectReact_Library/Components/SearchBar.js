import React, {useContext, useState, useEffect, useCallback} from 'react';
import { Searchbar, Text } from 'react-native-paper';
import { URL_API, fetchBooksStarted, fetchBooks } from '../context/Actions';
import AppContext from '../context/AppContext';
import {Image, ScrollView, StyleSheet, View} from "react-native";

const SearchBar = () => {
  const { dispatch } = useContext(AppContext);

  const [searchQuery, setSearchQuery] = useState('');
  const [error, setError] = useState(null);
  const [bookData, setBookData] = useState(null);

  const onChangeSearch = (query) => {
    setSearchQuery(query);
  };

  let imageUrl = bookData?.cover?.largeUrl?.replace('/api', '');

  const handleSearch = useCallback(async (query) => {
    if (query.length === 13) {

      const url = `${URL_API}/book/${query}`;
      console.log('URL da requisição ISBN:', url);
      setError(null);
      dispatch({ type: 'FETCH_BOOKS_STARTED' });

      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Livro não encontrado');
        }
        const data = await response.json();
        console.log('Livro encontrado:', data);
        setBookData(data);
      } catch (err) {
        setError('Erro ao buscar livro: ' + err.message);
      }
    } else if (query) {

      const url = `${URL_API}/search?page=0&query=${query}`;
      console.log('URL:', url);
      dispatch(fetchBooksStarted());
      const request = {};
      fetchBooks(url, request, dispatch);
    } else {
      setError('Por favor, insira um ISBN válido ou nome de livro.');
    }
  }, [dispatch]);

  useEffect(() => {
    if (searchQuery.length >= 3) {
      handleSearch(searchQuery);
    } else {
      setBookData(null);
    }
  }, [handleSearch, searchQuery]);

  return (
      <ScrollView>
        <Searchbar
            placeholder="Procura pelo Nome ou ISBN"
            onChangeText={onChangeSearch}
            value={searchQuery}
        />

        {error && <Text style={styles.errorText}>{error}</Text>}

        {bookData && (
            <View style={styles.bookDetails}>
              <Text style={styles.text}>ISBN: {bookData.isbn}</Text>
              <Text style={styles.text}>TITLE: {bookData.title}</Text>
              <Text style={styles.text}>DESCRIPTION: {bookData.description}</Text>
              <Text style={styles.text}>DATE: {bookData.publishDate}</Text>
              {bookData.cover && (
                  <Image
                      style={styles.tinyLogo}
                      source={{ uri: `http://193.136.62.24${imageUrl}` }}
                  />
              )}
            </View>
        )}
      </ScrollView>
  );
};

const styles = StyleSheet.create({
  tinyLogo: {
    marginTop: 15,
    marginBottom: 5,
    alignSelf: 'center',
    width: 200,
    height: 300,
  },
});

export default SearchBar;
