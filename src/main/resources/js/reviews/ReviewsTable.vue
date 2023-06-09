<template>
  <v-data-table
      :headers="headers"
      :items="this.reviews"
      :total-items="this.reviews.size"
      :rows-per-page-items="rowsPerPageItems"
      hide-action
      item-key="reviewTitle">
    <template slot="items" slot-scope="props">
      <tr @click="props.expanded = !props.expanded">
        <td>{{ props.item.reviewTitle }}</td>
        <td>{{ format(props.item.reviewDate) }}</td>
        <td>{{ props.item.reviewCountry }}</td>
        <td>{{ props.item.rating }}</td>
        <td>{{ props.item.sentiment }}</td>
      </tr>
    </template>
    <template slot="expand" slot-scope="props">
      <v-card flat>
        <v-card-title
            class="headline">
          {{ props.item.reviewTitle }}
        </v-card-title>
        <v-card-text>
          <v-layout row>
            {{ props.item.review}}
          </v-layout>
        </v-card-text>
      </v-card>
    </template>
  </v-data-table>
</template>

<script>

export default {
  name: 'reviewsTable',
  props: ['reviews'],

  data() {
    return{
      headers: [
        {
          text: 'Title',
          value: 'reviewTitle',
          align: 'left',
          sortable: false
        },
        { text: 'Date', value: 'reviewDate' },
        { text: 'Country', value: 'reviewCountry' },
        { text: 'Rating', value: 'rating' },
        { text: 'Sentiment', value: 'sentiment' }
      ],
      rowsPerPageItems: [10, 20, 30]
    }
  },
  created () {},
  computed: {},
  mounted () {},
  methods: {
    format (date) {
      date = new Date(date)
      const day = `${date.getUTCDate()}`.padStart(2, '0')
      const month = `${date.getUTCMonth() + 1}`.padStart(2, '0')
      const year = date.getFullYear()
      return `${month}/${day}/${year}`
    }
  },
  watch: {}
}
</script>
